package com.app.musicalbums.room.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.app.musicalbums.models.Album
import com.app.musicalbums.models.Artist
import com.app.musicalbums.models.Track

data class AlbumWithTracks(
    @Embedded val album: Album,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val tracks: List<Track>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val artist: Artist
)