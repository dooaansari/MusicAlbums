package com.app.musicalbums.helpers

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.app.musicalbums.R
import com.app.musicalbums.enums.ImageSize
import com.app.musicalbums.models.AlbumImage
import com.app.musicalbums.models.Image


fun ImageView.loadImage(list: List<Image>?, imageSize: ImageSize) {
    val smallImageUrl = list?.find { it.size == imageSize.name }
    smallImageUrl?.let {
        if (it.text.isNotBlank()) {
            load(it.text) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.baseline_image_black)
            }
        }
    }
}

fun ImageView.loadAlbumImage(image: AlbumImage?) {
    image?.let {
        if (it.text.isNotBlank()) {
            load(it.text) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.baseline_image_black)
            }
        }
    }
}

fun ImageView.loadImage(image: String?) {
    image?.let {
        if (it.isNotBlank()) {
            load(it) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.baseline_image_black)
            }
        }
    }
}