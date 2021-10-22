package com.app.musicalbums.adapters.viewholders

import android.graphics.drawable.Drawable
import android.util.Log
import com.app.musicalbums.base.BaseViewHolder
import com.app.musicalbums.contracts.IOnItemClick
import com.app.musicalbums.databinding.AlbumsRecyclerRowBinding
import com.app.musicalbums.enums.ImageSize
import com.app.musicalbums.helpers.loadImage
import com.app.musicalbums.models.Album
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.lang.Exception


class AlbumsViewHolder(private val binding: AlbumsRecyclerRowBinding) :
    BaseViewHolder(binding.root) {
    override fun bindView(data: Any?, onItemClick: IOnItemClick?) {
        if (data is Album) {
            with(data) {
                binding.albumName.text = name
                binding.image.loadImage(this.image, ImageSize.medium)


            }


        }
    }


}