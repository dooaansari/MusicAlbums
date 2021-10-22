package com.app.musicalbums.features.search.repository

interface SearchRepository {
    fun getArtistListDataSource(searchQuery: String) :ArtistDataSource
}