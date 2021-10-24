package com.app.musicalbums.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TrackResponse(
    @SerializedName("album")
    @Expose
    var album: TrackAlbum? = null
)