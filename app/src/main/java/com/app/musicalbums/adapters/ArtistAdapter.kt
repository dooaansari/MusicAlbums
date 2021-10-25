package com.app.musicalbums.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.app.musicalbums.adapters.viewholders.ArtistViewHolder
import com.app.musicalbums.base.BasePagingDataAdapter
import com.app.musicalbums.contracts.IOnItemClick
import com.app.musicalbums.databinding.ArtistRecyclerRowBinding
import com.app.musicalbums.models.Artist

class ArtistAdapter(onItemClick: IOnItemClick) :
    BasePagingDataAdapter<ArtistRecyclerRowBinding, Artist, ArtistViewHolder>(
        ArtistRecyclerRowBinding::class.java,
        ArtistViewHolder::class.java,
        DataDifferntiator,
        onItemClick
    ) {

    object DataDifferntiator : DiffUtil.ItemCallback<Artist>() {

        override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem.mbid == newItem.mbid
        }

        override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem == newItem
        }
    }

    override fun bindView(parent: ViewGroup): ViewBinding {
        return ArtistRecyclerRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    }


}