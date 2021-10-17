package com.app.musicalbums.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder (view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    abstract fun bindView(data: Any?)
    abstract fun itemClick(view: View?)

    override fun onClick(itemView: View?) {
      itemClick(itemView)
    }
}