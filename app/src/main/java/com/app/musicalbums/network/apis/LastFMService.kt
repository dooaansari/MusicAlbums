package com.app.musicalbums.network.apis

import com.app.musicalbums.models.ArtistSearchResponse
import com.app.musicalbums.models.TopAlbumsResponse
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
}