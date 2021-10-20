package com.app.musicalbums.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TopAlbums(
    @SerializedName("album")
    @Expose
    val album: List<Album>? = null,
    @SerializedName("@attr")
    @Expose
    val attr: Attr
)
