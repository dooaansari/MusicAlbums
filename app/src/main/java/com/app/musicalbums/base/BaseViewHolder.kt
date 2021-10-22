package com.app.musicalbums.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.app.musicalbums.contracts.IOnItemClick

abstract class BaseViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    var itemClickListener: IOnItemClick? = null

    abstract fun bindView(data: Any?, onItemClick: IOnItemClick? = null)

}