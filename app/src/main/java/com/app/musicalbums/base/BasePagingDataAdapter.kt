package com.app.musicalbums.base

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.app.musicalbums.contracts.IOnItemClick

abstract class BasePagingDataAdapter<V : ViewBinding, T : Any, E : BaseViewHolder>(
    val bindingView: Class<V>,
    val holderClass: Class<E>,
    dataDiffUtil: DiffUtil.ItemCallback<T>,
    var onItemClick: IOnItemClick
) :
    PagingDataAdapter<T, E>(dataDiffUtil) {
    abstract fun bindView(parent: ViewGroup): ViewBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): E {
        val binding = bindView(parent)
        return holderClass.getConstructor(bindingView).newInstance(binding)
    }

    override fun onBindViewHolder(holder: E, position: Int) {
        holder.bindView(getItem(position), onItemClick)
    }

}