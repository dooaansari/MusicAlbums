package com.app.musicalbums.features.main

import androidx.lifecycle.*
import androidx.paging.*
import com.app.musicalbums.R
import com.app.musicalbums.di.IoDispatcher
import com.app.musicalbums.features.main.repository.MainRepository
import com.app.musicalbums.features.search.repository.SearchRepository
import com.app.musicalbums.models.Album
import com.app.musicalbums.models.Artist
import com.app.musicalbums.network.apis.IOResponse
import com.app.musicalbums.room.entities.AlbumWithTracks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    val repository: MainRepository
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 30
    }

    var isEmptyList = true
    var clickedPosition = 0
    var deleteMessageId = MutableLiveData<Pair<Boolean,Int>>()

    val getFavouriteAlbums: () -> LiveData<PagingData<AlbumWithTracks>>? = {
        Pager(PagingConfig(pageSize = PAGE_SIZE)) {
            repository.getFavouritesDataSource()
        }.liveData.cachedIn(viewModelScope)

    }

    fun deleteAlbum(name: String?) {
        viewModelScope.launch {
            if (!name.isNullOrBlank()) {
                val result = repository.deleteAlbum(name)
                if (result is IOResponse.Success) {

                    deleteMessageId.value = Pair(true,R.string.album_delete_success)
                } else {
                    deleteMessageId.value = Pair(false,R.string.delete_album_error)
                }
            } else {
                deleteMessageId.value = Pair(false,R.string.delete_album_error)
            }
        }
    }

    fun resetObserver(){
        deleteMessageId.value = Pair(false, R.string.delete_album_error)
    }
}