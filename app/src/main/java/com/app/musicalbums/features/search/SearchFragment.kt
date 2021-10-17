package com.app.musicalbums.features.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.musicalbums.adapters.ArtistAdapter
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
        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.artistRecyclerview.apply {
            adapter = artistAdapter
            layoutManager = LinearLayoutManager(context)
        }
        setDataAdapter()
    }

    private fun setDataAdapter() {
        viewModel.listData.observe(viewLifecycleOwner, { data ->
            data?.let {
                lifecycleScope.launch {
                    artistAdapter.submitData(it)
                }
            }
        })
    }


}