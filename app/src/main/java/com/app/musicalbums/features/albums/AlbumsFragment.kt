package com.app.musicalbums.features.albums

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.musicalbums.R
import com.app.musicalbums.adapters.AlbumsAdapter
import com.app.musicalbums.adapters.LoadStateAdapter
import com.app.musicalbums.base.BaseFragment
import com.app.musicalbums.contracts.IOnItemClick
import com.app.musicalbums.databinding.AlbumsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlbumsFragment : BaseFragment<AlbumsFragmentBinding>(), IOnItemClick {

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
    }

    fun setLoadState(loadState: CombinedLoadStates){
        viewModel.isEmptyList =
            loadState.refresh is LoadState.NotLoading && albumsAdapter.itemCount == 0

        if (loadState.source.refresh is LoadState.Error) {
            binding.noData.text = context?.getString(R.string.unable_fetch)
        } else {
            binding.noData.text = context?.getString(R.string.no_data)

        }
        binding.noData.isVisible = viewModel.isEmptyList
        binding.loader.isVisible = loadState.source.refresh is LoadState.Loading
        binding.albumsRecyclerview.isVisible =
            loadState.source.refresh is LoadState.NotLoading && !viewModel.isEmptyList
    }

    private fun addLoadStateHandler(){
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

    fun getTopAlbums(artist: String?) {


        viewModel.getTopAlbums(artist)?.observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                albumsAdapter.submitData(it)
            }

     })
    }

    override fun onRecyclerItemClick(position: Int) {
        Log.i("tag",albumsAdapter.snapshot()[position]?.name?: "")
    }

}