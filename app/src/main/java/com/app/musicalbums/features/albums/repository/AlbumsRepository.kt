package com.app.musicalbums.features.albums.repository

import com.app.musicalbums.models.Album
import com.app.musicalbums.models.Artist
import com.app.musicalbums.models.Track
import com.app.musicalbums.models.TrackAlbum
import com.app.musicalbums.network.apis.IOResponse

interface AlbumsRepository {
    fun getAlbumsListDataSource(artistName: String): AlbumsDataSource
    suspend fun getAlbumTrack(artistName: String, albumName: String): IOResponse<TrackAlbum?>
    suspend fun insertAlbumWithTracks(artist: Artist, album: Album, tracks: List<Track>): IOResponse<Long>
}