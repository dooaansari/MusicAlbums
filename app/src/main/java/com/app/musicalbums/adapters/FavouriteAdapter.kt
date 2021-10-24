package com.app.musicalbums.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.app.musicalbums.adapters.viewholders.AlbumsViewHolder
import com.app.musicalbums.base.BasePagingDataAdapter
import com.app.musicalbums.contracts.IOnItemClick
import com.app.musicalbums.databinding.AlbumsRecyclerRowBinding
import com.app.musicalbums.room.entities.AlbumWithTracks

class FavouriteAdapter(onItemClick: IOnItemClick) :
    BasePagingDataAdapter<AlbumsRecyclerRowBinding, AlbumWithTracks, AlbumsViewHolder>(
        AlbumsRecyclerRowBinding::class.java,
        AlbumsViewHolder::class.java,
        DataDifferntiator,
        onItemClick
    ) {

    fun deleteFavouriteRow(position: Int) {
        snapshot()[position]?.album?.name = ""
        notifyItemChanged(position)
    }

    object DataDifferntiator : DiffUtil.ItemCallback<AlbumWithTracks>() {

        override fun areItemsTheSame(oldItem: AlbumWithTracks, newItem: AlbumWithTracks): Boolean {
            return oldItem.album.name == newItem.album.name
        }

        override fun areContentsTheSame(
            oldItem: AlbumWithTracks,
            newItem: AlbumWithTracks
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun bindView(parent: ViewGroup): ViewBinding {
        return AlbumsRecyclerRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    }


}