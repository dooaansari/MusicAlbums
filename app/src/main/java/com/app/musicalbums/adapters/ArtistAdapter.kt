package com.app.musicalbums.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.app.musicalbums.adapters.viewholders.ArtistViewHolder
import com.app.musicalbums.databinding.ArtistRecyclerRowBinding
import com.app.musicalbums.models.Artist


class ArtistAdapter : PagingDataAdapter<Artist, ArtistViewHolder>(DataDifferntiator)  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ArtistRecyclerRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        Log.i("tag", position.toString())
        holder.bindView(getItem(position))
    }


    object DataDifferntiator : DiffUtil.ItemCallback<Artist>() {

        override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem.mbid == newItem.mbid
        }

        override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem == newItem
        }
    }


}