package com.app.musicalbums.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bindView(data: Any?)

}