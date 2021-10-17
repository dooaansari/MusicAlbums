package com.app.musicalbums.network.apis

import com.app.musicalbums.models.ArtistSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMService {

    @GET(".")
    suspend fun getSearchResultsArtist(@Query("method") method:String, @Query("artist") artist: String, @Query("page") page: Int): Response<ArtistSearchResponse>
}