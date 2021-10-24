package com.app.musicalbums.adapters.viewholders

import android.view.View
import com.app.musicalbums.base.BaseViewHolder
import com.app.musicalbums.contracts.IOnItemClick
import com.app.musicalbums.databinding.ArtistRecyclerRowBinding
import com.app.musicalbums.enums.ImageSize
import com.app.musicalbums.helpers.loadImage
import com.app.musicalbums.models.Artist

class ArtistViewHolder(private val binding: ArtistRecyclerRowBinding) :
    BaseViewHolder(binding.root), View.OnClickListener {

    override fun bindView(data: Any?, onItemClick: IOnItemClick?) {
        itemClickListener = onItemClick
        binding.root.setOnClickListener(this)
        if (data is Artist) {
            with(data) {
                binding.artistName.text = name
                binding.image.loadImage(images, ImageSize.small)
            }


        }
    }

    override fun onClick(view: View?) {
        itemClickListener?.onRecyclerItemClick(bindingAdapterPosition)
    }


}