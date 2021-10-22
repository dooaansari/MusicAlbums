package com.app.musicalbums.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var artistId: Int? = null,
    var name: String? =null,
    var mbid: String? = null,
    var image: String? = null
)