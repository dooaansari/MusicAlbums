package com.app.musicalbums.di

import com.app.musicalbums.features.albums.repository.AlbumsRepository
import com.app.musicalbums.features.albums.repository.AlbumsRepositoryImpl
import com.app.musicalbums.features.main.repository.MainRepository
import com.app.musicalbums.features.main.repository.MainRepositoryImpl
import com.app.musicalbums.features.search.repository.SearchRepository
import com.app.musicalbums.features.search.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideSearchRepositoryImpl(repository: SearchRepositoryImpl): SearchRepository

    @Binds
    fun provideAlbumsRepositoryImpl(repository: AlbumsRepositoryImpl): AlbumsRepository

    @Binds
    fun provideMainRepositoryImpl(repository: MainRepositoryImpl): MainRepository
}