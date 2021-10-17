package com.app.musicalbums.contracts

import android.view.Menu
import androidx.core.view.children
import com.app.musicalbums.enums.ToolbarAction

interface IToolbar {
    var toolBarActions: ArrayList<ToolbarAction>

    fun setMenuItems(menu: Menu){
        val iterator = menu.children.iterator()
        while(iterator.hasNext()){
            val item = iterator.next()
            val isAvailable = toolBarActions.find {it.id == item.itemId }
            item.isVisible = when(isAvailable){
                null -> false
                else -> true
            }
        }
    }

    interface IAddAction{
        abstract fun addAction()
    }
}