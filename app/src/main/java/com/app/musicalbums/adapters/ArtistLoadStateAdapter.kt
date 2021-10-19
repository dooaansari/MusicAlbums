package com.app.musicalbums.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.app.musicalbums.adapters.viewholders.LoadStateHolder
import com.app.musicalbums.databinding.ArtistRecyclerErrorFooterBinding

class ArtistLoadStateAdapter : LoadStateAdapter<LoadStateHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateHolder {
        val binding = ArtistRecyclerErrorFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateHolder, loadState: LoadState) {
        holder.bindView(loadState)
    }



}