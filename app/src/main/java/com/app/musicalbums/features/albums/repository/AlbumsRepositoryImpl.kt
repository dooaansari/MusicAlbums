package com.app.musicalbums.features.albums.repository

import com.app.musicalbums.base.BaseRepository
import com.app.musicalbums.network.apis.LastFMService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsRepositoryImpl @Inject constructor(private val lastFMService: LastFMService) : BaseRepository(), AlbumsRepository {
    override fun getAlbumsListDataSource(artistName: String) = AlbumsDataSource(lastFMService, artistName)
}