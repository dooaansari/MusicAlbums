package com.app.musicalbums.features.albums

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.musicalbums.R
import com.app.musicalbums.adapters.AlbumsAdapter
import com.app.musicalbums.adapters.LoadStateAdapter
import com.app.musicalbums.base.BaseFragment
import com.app.musicalbums.contracts.IOnAlbumClick
import com.app.musicalbums.contracts.IOnItemClick
import com.app.musicalbums.databinding.AlbumsFragmentBinding
import com.app.musicalbums.enums.ImageSize
import com.app.musicalbums.features.search.SearchFragmentDirections
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlbumsFragment : BaseFragment<AlbumsFragmentBinding>(), IOnAlbumClick {

    var albumsAdapter: AlbumsAdapter = AlbumsAdapter(this)

    @Inject
    lateinit var loadStateAdapter: LoadStateAdapter

    val args: AlbumsFragmentArgs by navArgs()

    override val viewModel: AlbumsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AlbumsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        getTopAlbums(args.artist)
        addScreenLoaderObserver()
        setFavouriteObserver()
        setAlbumTrackObserver()
    }

    fun setViews(isLoaderVisible: Boolean, isRecyclerVisible: Boolean, isNoDataVisible: Boolean) {
        binding.noData.isVisible = isNoDataVisible
        binding.loaderMain.isVisible = isLoaderVisible
        binding.albumsRecyclerview.isVisible = isRecyclerVisible
    }

    fun setLoadState(loadState: CombinedLoadStates) {
        viewModel.isEmptyList = albumsAdapter.itemCount == 0
        val isLoading = loadState.source.refresh is LoadState.Loading

        if (loadState.source.refresh is LoadState.Error) {
            binding.noData.text = context?.getString(R.string.unable_fetch)
            setViews(false, false, true)
        } else {
            if (viewModel.isEmptyList) {
                binding.noData.text = context?.getString(R.string.no_data)
                setViews(isLoading, !isLoading && !viewModel.isEmptyList, !isLoading)
            } else {
                setViews(false, true, false)
            }

        }
    }

    private fun addLoadStateHandler() {
        albumsAdapter.addLoadStateListener { loadState ->
            setLoadState(loadState)
        }
    }

    private fun setRecyclerView() {
        addLoadStateHandler()
        binding.albumsRecyclerview.apply {
            adapter = albumsAdapter.withLoadStateFooter(loadStateAdapter)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun addScreenLoaderObserver() {
        viewModel.loadingStatus.observe(viewLifecycleOwner, {
            binding.loaderScreen.isVisible = it
        })
    }

    fun getTopAlbums(artist: String?) {
        viewModel.getTopAlbums(artist)?.observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                albumsAdapter.submitData(it)
            }

        })
    }

    fun setFavouriteObserver() {
        viewModel.favouriteAddResult.observe(viewLifecycleOwner, {
            if (it.first) {
                albumsAdapter.updateFavouriteRow(viewModel.clickedPosition)
            }
            this@AlbumsFragment.view?.let { view ->
                Snackbar.make(
                    view,
                    getString(it.second),
                    LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onFavouriteClick(position: Int) {
        albumsAdapter.snapshot()[position]?.let {
            viewModel.clickedPosition = position
            viewModel.setAlbumTracks(it)
        }
    }

    fun setAlbumTrackObserver() {
        viewModel.albumTracks.observe(viewLifecycleOwner, {
            val data = albumsAdapter.snapshot()[viewModel.clickedPosition]
            findNavController().navigate(
                AlbumsFragmentDirections.actionTopalbumsToDetails(
                    data?.artist?.name ?: "",
                    data?.name ?: "",
                    data?.images?.find { it.size == ImageSize.large.name }?.text ?: "",
                    it.toTypedArray()
                )
            )
        })
    }

    override fun onRecyclerItemClick(position: Int) {
        viewModel.clickedPosition = position
        val data = albumsAdapter.snapshot()[position]
        viewModel.getAlbumTracks(data?.artist?.name, data?.name)

    }

}