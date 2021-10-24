package com.app.musicalbums.features.main.repository

import androidx.paging.PagingSource
import com.app.musicalbums.models.Album
import com.app.musicalbums.network.apis.IOResponse
import com.app.musicalbums.room.entities.AlbumWithTracks

interface MainRepository {
  fun getFavouritesDataSource(): PagingSource<Int,AlbumWithTracks>
  suspend fun deleteAlbum(name: String): IOResponse<Boolean>
}