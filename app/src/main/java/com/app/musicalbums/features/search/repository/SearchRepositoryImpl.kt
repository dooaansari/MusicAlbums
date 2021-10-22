package com.app.musicalbums.features.search.repository

import com.app.musicalbums.base.BaseRepository
import com.app.musicalbums.features.search.repository.ArtistDataSource
import com.app.musicalbums.network.apis.LastFMService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class SearchRepositoryImpl @Inject constructor(private val lastFMService: LastFMService) : BaseRepository(), SearchRepository {
    override fun getArtistListDataSource(searchQuery: String) = ArtistDataSource(lastFMService, searchQuery)
}