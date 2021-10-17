package com.app.musicalbums.di

import com.app.musicalbums.adapters.ArtistAdapter
import com.app.musicalbums.features.login.LoginRepository
import com.app.musicalbums.features.search.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object SearchModule {

    @Provides
    @FragmentScoped
    fun providesArtistAdapter() = ArtistAdapter()

//    @Provides
//    @FragmentScoped
//    fun providesLoginRepository(searchRepository: SearchRepository) = searchRepository
}