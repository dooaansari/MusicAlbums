package com.app.musicalbums.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArtistMatches(@SerializedName("artist")
                         @Expose
                         var artist: List<Artist>?= null)
