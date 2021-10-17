package com.app.musicalbums.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArtistSearchResponse(@SerializedName("results")
                                @Expose
                                var results:ArtistResults?=null)
