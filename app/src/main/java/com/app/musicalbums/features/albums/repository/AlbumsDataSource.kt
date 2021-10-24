package com.app.musicalbums.features.albums.repository

import com.app.musicalbums.base.BasePagingAdapter
import com.app.musicalbums.models.Album
import com.app.musicalbums.models.Artist
import com.app.musicalbums.models.ArtistSearchResponse
import com.app.musicalbums.models.TopAlbumsResponse
import com.app.musicalbums.network.apis.LastFMService
import com.app.musicalbums.network.constants.FMApiMethods
import retrofit2.Response

class AlbumsDataSource(private val artistApiService: LastFMService,
                       private val artistName: String) : BasePagingAdapter<Album, TopAlbumsResponse>() {
    override fun loadData(response: Response<TopAlbumsResponse>) {
        val responseBody = response.body()
        list = responseBody?.topalbums?.album ?: emptyList()
        totalResults = responseBody?.topalbums?.attr?.total
        pageSize = responseBody?.topalbums?.attr?.perPage
    }

    override suspend fun callAPI(currentPage: Int): Response<TopAlbumsResponse> {
        val response = artistApiService.getTopAlbums(
            FMApiMethods.ARTIST_TOP_ALBUMS,
            artistName,
            currentPage
        )

        return response
    }
}