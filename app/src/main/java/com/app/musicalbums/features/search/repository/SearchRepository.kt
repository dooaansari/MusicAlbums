package com.app.musicalbums.features.search.repository

import com.app.musicalbums.base.BaseRepository
import com.app.musicalbums.features.search.repository.ArtistDataSource
import com.app.musicalbums.network.apis.LastFMService
import javax.inject.Inject

open class SearchRepository @Inject constructor(private val lastFMService: LastFMService) : BaseRepository() {
    fun getArtistListDataSource(searchQuery: String) = ArtistDataSource(lastFMService, searchQuery)
}