package com.app.musicalbums.adapters.viewholders

import android.view.View
import com.app.musicalbums.R
import com.app.musicalbums.base.BaseViewHolder
import com.app.musicalbums.contracts.IOnAlbumClick
import com.app.musicalbums.contracts.IOnItemClick
import com.app.musicalbums.databinding.AlbumsRecyclerRowBinding
import com.app.musicalbums.enums.ImageSize
import com.app.musicalbums.helpers.loadImage
import com.app.musicalbums.models.Album


class AlbumsViewHolder(private val binding: AlbumsRecyclerRowBinding) :
    BaseViewHolder(binding.root), View.OnClickListener {
    override fun bindView(data: Any?, onItemClick: IOnItemClick?) {
        itemClickListener = onItemClick
        binding.root.setOnClickListener(this)
        binding.favourite.setOnClickListener(this)
        if (data is Album) {
            with(data) {
                binding.albumName.text = name
                binding.image.loadImage(this.images, ImageSize.medium)
            }
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