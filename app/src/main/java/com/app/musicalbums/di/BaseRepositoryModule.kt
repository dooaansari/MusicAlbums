package com.app.musicalbums.di

import android.content.Context
import com.app.musicalbums.base.BaseRepository
import com.app.musicalbums.helpers.Store
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BaseRepositoryModule {

    @Provides
    fun providesBaseRepository(repository: BaseRepository) = repository

    @Provides
    @Singleton
    fun provideStore(@ApplicationContext context: Context) = Store(context)
}