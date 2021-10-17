package com.app.musicalbums.base

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.app.musicalbums.R
import com.app.musicalbums.contracts.IToolbar
import com.app.musicalbums.enums.ToolbarAction
import com.app.musicalbums.features.main.MainFragment
import com.app.musicalbums.features.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

abstract class BaseFragment<T: ViewBinding> : Fragment(), IToolbar {

    protected lateinit var binding: T
    protected abstract val viewModel:ViewModel

    override var toolBarActions = arrayListOf<ToolbarAction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                findNavController().navigateAction(MainFragmentDirections::actionMainToSearch)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu){
        super.onPrepareOptionsMenu(menu)
        setMenuItems(menu)
    }

    protected fun <T:() -> NavDirections> NavController.navigateAction(action: T) {
        navigate(action())
    }


}
