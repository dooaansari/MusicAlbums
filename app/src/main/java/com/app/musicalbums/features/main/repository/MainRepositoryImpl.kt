package com.app.musicalbums.features.main.repository

import com.app.musicalbums.base.BaseRepository
import com.app.musicalbums.network.apis.IOResponse
import com.app.musicalbums.room.dao.AlbumDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class MainRepositoryImpl @Inject constructor(
    override val albumDao: AlbumDao
) : BaseRepository(), MainRepository {
    override fun getFavouritesDataSource() = albumDao.getAllAlbums()
    override suspend fun deleteAlbum(name: String): IOResponse<Boolean> {
        try {
            albumDao.deleteAlbum(name)
            return IOResponse.Success(true)
        } catch (ex: Exception) {
            return IOResponse.Error(ex)
        }
    }


}