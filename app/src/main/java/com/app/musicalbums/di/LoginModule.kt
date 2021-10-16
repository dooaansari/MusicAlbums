package com.app.musicalbums.di

import androidx.fragment.app.Fragment
import com.app.musicalbums.contracts.IView
import com.app.musicalbums.features.login.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(FragmentComponent::class)
class LoginModule {

    @Provides
    @FragmentScoped
    fun providesLoginView(fragment: Fragment) = fragment as IView

    @Provides
    @FragmentScoped
    fun providesLoginRepository(loginRepository: LoginRepository) = loginRepository

    @Provides
    @FragmentScoped
    fun providesDispatcher(dispatcher: CoroutineDispatcher) = dispatcher
}