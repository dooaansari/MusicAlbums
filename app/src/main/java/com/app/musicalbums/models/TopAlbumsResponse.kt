package com.app.musicalbums.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class TopAlbumsResponse(
    @SerializedName("topalbums")
    @Expose
    var topalbums: TopAlbums? = null
)