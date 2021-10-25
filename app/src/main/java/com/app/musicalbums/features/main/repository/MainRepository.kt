package com.app.musicalbums.features.main.repository

import androidx.paging.PagingSource
import com.app.musicalbums.network.apis.IOResponse
import com.app.musicalbums.room.dao.AlbumDao
import com.app.musicalbums.room.entities.AlbumWithTracks

interface MainRepository {
    val albumDao: AlbumDao

    fun getFavouritesDataSource(): PagingSource<Int, AlbumWithTracks>
    suspend fun deleteAlbum(name: String): IOResponse<Boolean>
}