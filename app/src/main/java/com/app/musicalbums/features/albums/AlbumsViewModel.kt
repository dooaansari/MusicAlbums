package com.app.musicalbums.features.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.app.musicalbums.di.IoDispatcher
import com.app.musicalbums.features.albums.repository.AlbumsRepository
import com.app.musicalbums.models.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    val repository: AlbumsRepository
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 50
    }


    var isInitialLoad = true
    var isEmptyList = true

    val getTopAlbums: (artist: String?) -> LiveData<PagingData<Album>>? = { artist ->
        if (!artist.isNullOrBlank()) {
            isInitialLoad = false
            Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                val data = repository.getAlbumsListDataSource(artist.trim())
                data
            }.liveData.cachedIn(viewModelScope)
        } else null
    }


}