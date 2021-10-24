package com.app.musicalbums.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "album_image")
data class AlbumImage(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var albumName: String,
    var text: String,
    var size: String
)
