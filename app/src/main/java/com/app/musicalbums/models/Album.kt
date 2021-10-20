package com.app.musicalbums.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("playcount")
    @Expose
    val playcount: Int,
    @SerializedName("mbid")
    @Expose
    val mbid: String,
    @SerializedName("url")
    @Expose
    val url: String,
    @SerializedName("artist")
    @Expose
    val artist: Artist,
    @SerializedName("image")
    @Expose
    val image: List<Image>? = null
)
