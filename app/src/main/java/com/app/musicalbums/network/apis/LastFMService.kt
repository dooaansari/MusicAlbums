package com.app.musicalbums.network.apis

import com.app.musicalbums.models.Album
import com.app.musicalbums.models.ArtistSearchResponse
import com.app.musicalbums.models.TopAlbumsResponse
import com.app.musicalbums.models.TrackResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMService {

    @GET(".")
    suspend fun getSearchResultsArtist(
        @Query("method") method: String,
        @Query("artist") artist: String,
        @Query("page") page: Int
    ): Response<ArtistSearchResponse>

    @GET(".")
    suspend fun getTopAlbums(
        @Query("method") method: String,
        @Query("artist") artist: String,
        @Query("page") page: Int
    ): Response<TopAlbumsResponse>

    @GET(".")
    suspend fun getAlbumTracks(
        @Query("method") method: String,
        @Query("artist") artist: String,
        @Query("album") album: String,
    ): Response<TrackResponse>
}