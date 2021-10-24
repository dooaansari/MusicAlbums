package com.app.musicalbums.features.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.musicalbums.R
import com.app.musicalbums.adapters.FavouriteAdapter
import com.app.musicalbums.adapters.LoadStateAdapter
import com.app.musicalbums.base.BaseFragment
import com.app.musicalbums.contracts.IOnAlbumClick
import com.app.musicalbums.contracts.IToolbar
import com.app.musicalbums.databinding.MainFragmentBinding
import com.app.musicalbums.enums.ImageSize
import com.app.musicalbums.enums.ToolbarAction
import com.app.musicalbums.features.albums.AlbumsFragmentDirections
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>(), IToolbar.IAddAction, IOnAlbumClick {
    override val viewModel: MainViewModel by viewModels()

    var favouriteAdapter: FavouriteAdapter = FavouriteAdapter(this)

    @Inject
    lateinit var loadStateAdapter: LoadStateAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        addAction()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        getTopAlbums()
        setDeleteObserver()
    }

    fun getTopAlbums() {
        viewModel.getFavouriteAlbums()?.observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                favouriteAdapter.submitData(it)
            }

        })
    }

    private fun setRecyclerView() {
        addLoadStateHandler()
        binding.favouriteAlbumsRecyclerview.apply {
            adapter = favouriteAdapter.withLoadStateFooter(loadStateAdapter)
            layoutManager = LinearLayoutManager(context)
        }
    }


    fun setViews(isLoaderVisible: Boolean, isRecyclerVisible: Boolean, isNoDataVisible: Boolean) {
        binding.noData.isVisible = isNoDataVisible
        binding.loaderMain.isVisible = isLoaderVisible
        binding.favouriteAlbumsRecyclerview.isVisible = isRecyclerVisible
    }

    fun setLoadState(loadState: CombinedLoadStates) {
        viewModel.isEmptyList = favouriteAdapter.itemCount == 0
        val isLoading = loadState.source.refresh is LoadState.Loading

        if (loadState.source.refresh is LoadState.Error) {
            binding.noData.text = context?.getString(R.string.unable_fetch)
            setViews(false, false, true)
        } else {
            if (viewModel.isEmptyList) {
                binding.noData.text = context?.getString(R.string.no_favourites)
                setViews(isLoading, !isLoading && !viewModel.isEmptyList, !isLoading)
            } else {
                setViews(false, true, false)
            }

        }
    }

    private fun addLoadStateHandler() {
        favouriteAdapter.addLoadStateListener { loadState ->
            setLoadState(loadState)
        }
    }

    override fun addAction() {
        toolBarActions.add(ToolbarAction.SEARCH)
    }

    override fun onFavouriteClick(position: Int) {
        val data = favouriteAdapter.snapshot()[position]
        viewModel.deleteAlbum(data?.album?.name)
    }

    override fun onRecyclerItemClick(position: Int) {
        val data = favouriteAdapter.snapshot()[position]
        findNavController().navigate(
            MainFragmentDirections.actionMainToDetails(
                data?.artist?.name ?: "",
                data?.album?.name ?: "",
                data?.image?.find { it.size == ImageSize.large.name }?.text ?: "",
                data?.tracks?.toTypedArray() ?: emptyArray()
            )
        )
    }

    fun setDeleteObserver(){
        viewModel.deleteMessageId.observe(viewLifecycleOwner, {
            favouriteAdapter.deleteFavouriteRow(viewModel.clickedPosition)
            if(it.first){

            }
            this@MainFragment.view?.let { view ->
                Snackbar.make(
                    view,
                    getString(it.second),
                    BaseTransientBottomBar.LENGTH_SHORT
                ).show()
            }
        })
    }


}