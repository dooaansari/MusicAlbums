package com.app.musicalbums.features.albums.repository

import com.app.musicalbums.base.BaseRepository
import com.app.musicalbums.models.Album
import com.app.musicalbums.models.Artist
import com.app.musicalbums.models.Track
import com.app.musicalbums.network.apis.LastFMService
import com.app.musicalbums.network.apis.IOResponse
import com.app.musicalbums.network.constants.FMApiMethods
import com.app.musicalbums.network.exceptions.parseError
import com.app.musicalbums.room.dao.AlbumDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsRepositoryImpl @Inject constructor(
    private val lastFMService: LastFMService,
    private val albumDao: AlbumDao
) : BaseRepository(), AlbumsRepository {
    override fun getAlbumsListDataSource(artistName: String) =
        AlbumsDataSource(lastFMService, artistName)

    override suspend fun getAlbumTrack(artistName: String): IOResponse<Album?> {
        try {
            val response = lastFMService.getAlbumTracks(FMApiMethods.ALBUM_TRACKS, artistName)
            return if (response.isSuccessful) {
                val body = response.body()
                IOResponse.Success(body)
            } else {
                IOResponse.Error(parseError(error = response.errorBody()?.string()))
            }
        } catch (ex: Exception) {
            return IOResponse.Error(ex)
        }

    }

    override suspend fun insertAlbumWithTracks(
        artist: Artist,
        album: Album,
        tracks: List<Track>
    ): IOResponse<Long> {
        return try {
            albumDao.insertAlbumsWithTracks(artist, album, tracks)
            IOResponse.Success(1)
        } catch (ex: Exception) {
            IOResponse.Error(ex)
        }
    }


}