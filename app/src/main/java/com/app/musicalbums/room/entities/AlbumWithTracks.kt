package com.app.musicalbums.room.entities

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.app.musicalbums.models.Album
import com.app.musicalbums.models.AlbumImage
import com.app.musicalbums.models.Artist
import com.app.musicalbums.models.Track

data class AlbumWithTracks(
    @Embedded val album: Album,
    @Relation(
        parentColumn = "name",
        entityColumn = "albumName"
    )
    val tracks: List<Track>,

    @Relation(
        parentColumn = "mbid",
        entityColumn = "mbid"
    )
    val artist: Artist,

    @Relation(
        parentColumn = "name",
        entityColumn = "albumName"
    )
    val image: List<AlbumImage>,

)