package com.app.musicalbums.features.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.app.musicalbums.di.IoDispatcher
import com.app.musicalbums.features.search.repository.SearchRepository
import com.app.musicalbums.models.Artist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val repository: SearchRepository
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 30
    }

    var previousSearchQuery = ""
    var isInitialLoad = true

    val getSearchedArtist: (query: String?) -> LiveData<PagingData<Artist>>? = { searchQuery ->
        isInitialLoad = false
        if (!searchQuery.isNullOrBlank() && previousSearchQuery != searchQuery) {
            Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                repository.getArtistListDataSource(searchQuery.trim())
            }.liveData.cachedIn(viewModelScope)
        } else null
    }


}