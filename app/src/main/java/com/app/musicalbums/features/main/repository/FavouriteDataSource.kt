package com.app.musicalbums.features.main.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.musicalbums.helpers.getNextPage
import com.app.musicalbums.models.Album
import com.app.musicalbums.room.dao.AlbumDao
import com.app.musicalbums.room.entities.AlbumWithTracks

class FavouriteDataSource(private val albumDao: AlbumDao) {

//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AlbumWithTracks> {
//        try {
//            val currentPage = params.key ?: 1
//            val albums = albumDao.getAllAlbums(params.loadSize)
//            val prevKey = if (currentPage == 1) null else currentPage - 1
//            return LoadResult.Page(
//                data = albums,
//                prevKey = prevKey,
//                nextKey = getNextPage(albums.size, params.loadSize, currentPage)
//            )
//        } catch (ex: Exception) {
//            return LoadResult.Error(ex)
//        }
//
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, AlbumWithTracks>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//    }
}