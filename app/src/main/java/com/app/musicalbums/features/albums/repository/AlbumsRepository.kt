package com.app.musicalbums.features.albums.repository

import com.app.musicalbums.models.Album

interface AlbumsRepository {
    fun getAlbumsListDataSource(artistName: String): AlbumsDataSource
}