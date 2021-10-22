package com.app.musicalbums.di

import com.app.musicalbums.adapters.ArtistAdapter
import com.app.musicalbums.adapters.LoadStateAdapter
import com.app.musicalbums.features.search.SearchFragment
import com.app.musicalbums.features.search.repository.SearchRepository
import com.app.musicalbums.features.search.repository.SearchRepositoryImpl
import com.app.musicalbums.network.apis.LastFMService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(FragmentComponent::class)
object RecyclerAdapterModule {

    @Provides
    @FragmentScoped
    fun providesLoadStateAdapter() = LoadStateAdapter()
}
