package com.app.musicalbums.di

import com.app.musicalbums.adapters.LoadStateAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object RecyclerAdapterModule {

    @Provides
    @FragmentScoped
    fun providesLoadStateAdapter() = LoadStateAdapter()
}
