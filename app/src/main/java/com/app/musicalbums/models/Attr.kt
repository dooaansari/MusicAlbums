package com.app.musicalbums.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Attr(
    @SerializedName("artist")
    @Expose
    val artist: String,
    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("perPage")
    @Expose
    val perPage: Int,
    @SerializedName("totalPages")
    @Expose
    val totalPages: Int,
    @SerializedName("total")
    @Expose val total: Int
)