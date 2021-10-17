package com.app.musicalbums.features.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.musicalbums.base.BaseFragment
import com.app.musicalbums.contracts.IToolbar
import com.app.musicalbums.databinding.MainFragmentBinding
import com.app.musicalbums.enums.ToolbarAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>(), IToolbar.IAddAction {
    override val viewModel: MainViewModel by viewModels()



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
    }

    override fun addAction() {
        toolBarActions.add(ToolbarAction.SEARCH)
    }



}