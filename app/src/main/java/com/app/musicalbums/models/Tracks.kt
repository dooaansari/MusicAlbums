package com.app.musicalbums.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Tracks(@SerializedName("track")
                  @Expose
                  val track: List<Track>?= null)