package com.app.musicalbums.features.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.musicalbums.R
import com.app.musicalbums.adapters.ArtistAdapter
import com.app.musicalbums.adapters.ArtistLoadStateAdapter
import com.app.musicalbums.base.BaseFragment
import com.app.musicalbums.contracts.IToolbar
import com.app.musicalbums.databinding.SearchFragmentBinding
import com.app.musicalbums.enums.ToolbarAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchFragmentBinding>() {
    @Inject
    lateinit var artistAdapter: ArtistAdapter

    @Inject
    lateinit var artistLoadStateAdapter: ArtistLoadStateAdapter

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
        setOnClickListenerSearchButton()
        setRecyclerView()
    }

    fun setLoadState(loadState: CombinedLoadStates, isInitialLoad: Boolean){
        viewModel.isEmptyList =
            loadState.refresh is LoadState.NotLoading && artistAdapter.itemCount == 0

        if (loadState.source.refresh is LoadState.Error) {
            binding.noData.text = context?.getString(R.string.unable_fetch)
        } else {
            binding.noData.text = context?.getString(R.string.no_data)

        }
        binding.noData.isVisible = viewModel.isEmptyList && !isInitialLoad
        binding.loader.isVisible = loadState.source.refresh is LoadState.Loading
        binding.artistRecyclerview.isVisible =
            loadState.source.refresh is LoadState.NotLoading && !viewModel.isEmptyList
    }

    private fun addLoadStateHandler(){
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
        viewModel.getSearchedArtist(searchQuery)?.observe(viewLifecycleOwner, { data ->
            data?.let {
                lifecycleScope.launch {
                    artistAdapter.submitData(it)
                }
            }
        })
    }


}