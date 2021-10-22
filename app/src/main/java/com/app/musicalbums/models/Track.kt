package com.app.musicalbums.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "track")
data class Track(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @SerializedName("duration")
    @Expose
    val duration: Int? = 0,
    @SerializedName("url")
    @Expose
    val url: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,

    var albumId: Long? = 0
)