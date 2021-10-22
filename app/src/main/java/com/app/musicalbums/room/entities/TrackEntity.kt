package com.app.musicalbums.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var albumId: Int? = null,
    var name: String? =null,
    var duration: Int? = null,
    var url: String? = null
)