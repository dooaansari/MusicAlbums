package com.app.musicalbums.features.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.app.musicalbums.di.IoDispatcher
import com.app.musicalbums.features.search.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(@IoDispatcher private val ioDispatcher: CoroutineDispatcher, private val repository: SearchRepository) : ViewModel() {
    var searchQuery = "cher"
    val pager = Pager(PagingConfig(pageSize = 50)) {
        Log.i("tag", "calling")
        repository.getArtistListDataSource(searchQuery)
    }
    val listData = Pager(PagingConfig(pageSize = 30)) {
       Log.i("tag", "calling")
       repository.getArtistListDataSource(searchQuery)
    }.liveData.cachedIn(viewModelScope)

}