package com.app.musicalbums.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseError(
    @SerializedName("code")
    @Expose
    var code: Int? = null,

    @SerializedName("error")
    @Expose
    var error: String? = null,
)