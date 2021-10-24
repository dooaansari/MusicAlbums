package com.app.musicalbums.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.app.musicalbums.adapters.viewholders.AlbumsViewHolder
import com.app.musicalbums.base.BasePagingDataAdapter
import com.app.musicalbums.contracts.IOnItemClick
import com.app.musicalbums.databinding.AlbumsRecyclerRowBinding
import com.app.musicalbums.models.Album

class AlbumsAdapter(onItemClick: IOnItemClick) : BasePagingDataAdapter<AlbumsRecyclerRowBinding, Album, AlbumsViewHolder>(
    AlbumsRecyclerRowBinding::class.java,
    AlbumsViewHolder::class.java,
    DataDifferntiator,
    onItemClick
) {

    fun updateFavouriteRow(position:Int){
        snapshot()[position]?.isFavourite = true
        notifyItemChanged(position)
    }

    object DataDifferntiator : DiffUtil.ItemCallback<Album>() {

        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.mbid == newItem.mbid
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }

    override fun bindView(parent: ViewGroup): ViewBinding {
        return AlbumsRecyclerRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    }


}