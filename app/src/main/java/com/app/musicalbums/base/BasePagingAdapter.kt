package com.app.musicalbums.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.musicalbums.helpers.getNextPage
import com.app.musicalbums.network.exceptions.parseError
import retrofit2.Response

abstract class BasePagingAdapter<T : Any, E : Any>() : PagingSource<Int, T>() {
    abstract fun loadData(response: Response<E>)
    abstract suspend fun callAPI(currentPage: Int): Response<E>

    var totalResults: Int? = 0
    var pageSize: Int? = 0
    var list = listOf<T>()
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        try {
            val currentPage = params.key ?: 1
            val response = callAPI(currentPage)
            if (response.isSuccessful) {
                loadData(response)
                val prevKey = if (currentPage == 1) null else currentPage - 1

                return LoadResult.Page(
                    data = list,
                    prevKey = prevKey,
                    nextKey = getNextPage(totalResults, pageSize, currentPage)
                )
            } else {
                return LoadResult.Error(parseError(error = response.errorBody()?.string()))
            }

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}