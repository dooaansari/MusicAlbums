package com.app.musicalbums.features.search.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.musicalbums.models.Artist
import com.app.musicalbums.models.ArtistSearchResponse
import com.app.musicalbums.network.apis.LastFMService
import com.app.musicalbums.network.constants.FMApiMethods
import com.app.musicalbums.network.exceptions.parseError
import okhttp3.internal.parseCookie

class ArtistDataSource(private val artistApiService: LastFMService, private val searchQuery:String): PagingSource<Int, Artist>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        try {
            val currentPage = params.key ?: 1
            val response = artistApiService.getSearchResultsArtist(FMApiMethods.SEARCH_ARTIST, searchQuery, currentPage)
            if(response.isSuccessful){
                val responseBody = response.body()
                val artistList = responseBody?.results?.artistmatches?.artist ?: emptyList()
                val totalResults = responseBody?.results?.opensearchTotalResults
                val pageSize = responseBody?.results?.opensearchItemsPerPage

                val prevKey = if (currentPage == 1) null else currentPage - 1

                return LoadResult.Page(
                    data = artistList,
                    prevKey = prevKey,
                    nextKey = getNextPage(totalResults,pageSize,currentPage)
                )
            }else{
                //Log.i("tag","error")
                //Log.i("tag",response?.code().toString())
                //Log.i("tag",response?.errorBody()?.string().toString())
                parseError(error = response.errorBody()?.string(), httpCode = response.code())
                // create a new type of exception to be thrown in this case to parse data
                return LoadResult.Error(Exception())
            }

        } catch (e: Exception) {
            Log.i("tag","error"+e.message)
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    fun getNextPage(totalResult: Int?, pageSize: Int?, currentPage: Int): Int? {
        totalResult?.let {
          pageSize?.let {
              return when(totalResult > pageSize*currentPage){
                  true -> currentPage.plus(1)
                  else -> null
              }
          }
        }
        return null
    }


}