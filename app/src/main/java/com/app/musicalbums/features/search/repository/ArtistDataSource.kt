package com.app.musicalbums.features.search.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.musicalbums.models.Artist
import com.app.musicalbums.models.ArtistSearchResponse
import com.app.musicalbums.network.apis.LastFMService
import com.app.musicalbums.network.constants.FMApiMethods

class ArtistDataSource(private val artistApiService: LastFMService, private val searchQuery:String=""): PagingSource<Int, Artist>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        try {
            Log.i("tag","loading data")
            val currentPage = params.key ?: 1
            val response = artistApiService.getSearchResultsArtist(FMApiMethods.SEARCH_ARTIST, searchQuery, currentPage)
            Log.i("tag",response.body().toString())
            if(response.isSuccessful){
                val artistList = response.body()?.results?.artistmatches?.artist ?: emptyList()
                val prevKey = if (currentPage == 1) null else currentPage - 1

                return LoadResult.Page(
                    data = artistList,
                    prevKey = prevKey,
                    nextKey = currentPage.plus(1)
                )
            }else{
                Log.i("tag","error")
                throw Exception()
            }

        } catch (e: Exception) {
            Log.i("tag","error"+e.message)
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        TODO("Not yet implemented")
    }


}