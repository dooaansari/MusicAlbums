package com.app.musicalbums.features.search

import androidx.lifecycle.*
import androidx.paging.*
import com.app.musicalbums.di.IoDispatcher
import com.app.musicalbums.features.search.repository.SearchRepository
import com.app.musicalbums.models.Artist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
open class SearchViewModel @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    val repository: SearchRepository
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 30
    }

    var previousSearchQuery = ""
    var isInitialLoad = true
    var isEmptyList = true
    var persistLiveData: LiveData<PagingData<Artist>>? = null

    val getSearchedArtist: (query: String?) -> LiveData<PagingData<Artist>>? = { searchQuery ->
        isInitialLoad = false
        if (!searchQuery.isNullOrBlank()) {
            persistLiveData = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                repository.getArtistListDataSource(searchQuery.trim())
            }.liveData.map { it.filter { it.mbid.isNotBlank() } }.cachedIn(viewModelScope)
           persistLiveData
        }else null
    }
}