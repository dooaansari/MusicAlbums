package com.app.musicalbums.adapters.viewholders

import android.view.View
import com.app.musicalbums.R
import com.app.musicalbums.base.BaseViewHolder
import com.app.musicalbums.contracts.IOnAlbumClick
import com.app.musicalbums.contracts.IOnItemClick
import com.app.musicalbums.databinding.AlbumsRecyclerRowBinding
import com.app.musicalbums.enums.ImageSize
import com.app.musicalbums.helpers.loadAlbumImage
import com.app.musicalbums.helpers.loadImage
import com.app.musicalbums.models.Album
import com.app.musicalbums.room.entities.AlbumWithTracks


class AlbumsViewHolder(private val binding: AlbumsRecyclerRowBinding) :
    BaseViewHolder(binding.root), View.OnClickListener {
    override fun bindView(data: Any?, onItemClick: IOnItemClick?) {
        itemClickListener = onItemClick
        binding.root.setOnClickListener(this)
        binding.favourite.setOnClickListener(this)
        val albumData = when (data is Album) {
            true -> data
            else -> (data as AlbumWithTracks).album

        }
        with(albumData) {
            binding.albumName.text = name

            if (this.isFavourite) {
                binding.favourite.setText(R.string.icon_heart)
            } else {
                binding.favourite.setText(R.string.icon_heart_unfilled)
            }
        }
        if (data is Album) {
            binding.image.loadImage(albumData.images, ImageSize.medium)

        } else if (data is AlbumWithTracks) {
            binding.favourite.setText(R.string.icon_heart)
            binding.image.loadAlbumImage(data.image.find { it.size == ImageSize.medium.name })
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.favourite -> (itemClickListener as? IOnAlbumClick)?.onFavouriteClick(
                bindingAdapterPosition
            )
            else -> itemClickListener?.onRecyclerItemClick(bindingAdapterPosition)
        }
    }


}