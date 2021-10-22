package com.app.musicalbums.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artist")
data class ArtistEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String? =null,
    var mbid: String? = null,
    var image: String? = null
)