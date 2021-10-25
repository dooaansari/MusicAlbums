package com.app.musicalbums.features.search.repository

import com.app.musicalbums.base.BasePagingAdapter
import com.app.musicalbums.models.Artist
import com.app.musicalbums.models.ArtistSearchResponse
import com.app.musicalbums.network.apis.LastFMService
import com.app.musicalbums.network.constants.FMApiMethods
import retrofit2.Response

open class ArtistDataSource(
    private val artistApiService: LastFMService,
    private val searchQuery: String
) : BasePagingAdapter<Artist, ArtistSearchResponse>() {

    override fun loadData(response: Response<ArtistSearchResponse>) {
        val responseBody = response.body()
        list = responseBody?.results?.artistmatches?.artist ?: emptyList()
        totalResults = responseBody?.results?.opensearchTotalResults
        pageSize = responseBody?.results?.opensearchItemsPerPage
    }

    override suspend fun callAPI(currentPage: Int): Response<ArtistSearchResponse> {
        val response = artistApiService.getSearchResultsArtist(
            FMApiMethods.SEARCH_ARTIST,
            searchQuery,
            currentPage
        )

        return response
    }


}