package com.app.musicalbums.features.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.musicalbums.R
import com.app.musicalbums.adapters.ArtistAdapter
import com.app.musicalbums.adapters.LoadStateAdapter
import com.app.musicalbums.base.BaseFragment
import com.app.musicalbums.contracts.IOnItemClick
import com.app.musicalbums.databinding.SearchFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchFragmentBinding>(), IOnItemClick {

    var artistAdapter: ArtistAdapter = ArtistAdapter(this)

    @Inject
    lateinit var artistLoadStateAdapter: LoadStateAdapter

    override val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle(context?.getString(R.string.artist))
        setOnClickListenerSearchButton()
        setRecyclerView()
        setPersistDataEvents()
    }

    fun setPersistDataEvents(){
        viewModel.searchResults.observe(viewLifecycleOwner, {
            viewLifecycleOwner.lifecycleScope.launch {
                artistAdapter.submitData(it)
            }

        })
    }

    fun setViews(isLoaderVisible: Boolean, isRecyclerVisible: Boolean, isNoDataVisible: Boolean) {
        binding.noData.isVisible = isNoDataVisible
        binding.loader.isVisible = isLoaderVisible
        binding.artistRecyclerview.isVisible = isRecyclerVisible
    }

    fun setLoadState(loadState: CombinedLoadStates, isInitialLoad: Boolean) {
        viewModel.isEmptyList = artistAdapter.itemCount == 0
        val isLoading = loadState.source.refresh is LoadState.Loading

        if (loadState.source.refresh is LoadState.Error) {
            binding.noData.text = context?.getString(R.string.unable_fetch)
            setViews(false, false, true)
        } else {
            if (viewModel.isEmptyList && !isInitialLoad) {
                binding.noData.text = context?.getString(R.string.no_data)
                setViews(isLoading, !isLoading && !viewModel.isEmptyList, !isLoading)
            } else {
                setViews(false, true, false)
            }

        }
    }

    private fun addLoadStateHandler() {
        artistAdapter.addLoadStateListener { loadState ->
            setLoadState(loadState, viewModel.isInitialLoad)
        }
    }

    private fun setRecyclerView() {
        addLoadStateHandler()
        binding.artistRecyclerview.apply {
            adapter = artistAdapter.withLoadStateFooter(artistLoadStateAdapter)
            layoutManager = LinearLayoutManager(context)
        }

    }

    private fun setOnClickListenerSearchButton() {
        binding.searchComponent.searchButtonClick = ::onSearchButtonClick
    }

    fun onSearchButtonClick(query: String) {
        searchArtist(query)
        viewModel.previousSearchQuery = query
    }

    fun searchArtist(searchQuery: String) {
        viewModel.setSearchQuery(searchQuery)
//        viewModel.getSearchedArtist(searchQuery)?.observe(viewLifecycleOwner, { data ->
//            data?.let {
//                if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED))
//                    viewLifecycleOwner.lifecycleScope.launch {
//                        artistAdapter.submitData(it)
//                    }
//            }
//        })
    }

    override fun onRecyclerItemClick(position: Int) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchToTopalbums(
                artistAdapter.snapshot()[position]?.name ?: ""
            )
        )
    }


}