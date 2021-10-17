package com.app.musicalbums.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("#text")
    @Expose
    var text: String,
    @SerializedName("size")
    @Expose
    var size: String
)
