package com.app.musicalbums.features.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.app.musicalbums.R
import com.app.musicalbums.di.IoDispatcher
import com.app.musicalbums.features.albums.repository.AlbumsRepository
import com.app.musicalbums.models.Album
import com.app.musicalbums.models.Track
import com.app.musicalbums.network.apis.IOResponse
import com.app.musicalbums.network.exceptions.runTimeExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    val repository: AlbumsRepository
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 50
    }

    val loadingStatus = MutableLiveData<Boolean>()

    var isInitialLoad = true
    var isEmptyList = true
    var messageId = 0

    val getTopAlbums: (artist: String?) -> LiveData<PagingData<Album>>? = { artist ->
        if (!artist.isNullOrBlank()) {
            isInitialLoad = false
            Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                val data = repository.getAlbumsListDataSource(artist.trim())
                data
            }.liveData.cachedIn(viewModelScope)
        } else null
    }

    suspend fun insertAlbumsWithTracks(album: Album, tracks: List<Track>): Int {
        val dbResponse = repository.insertAlbumWithTracks(
            album.artist,
            album,
            tracks
        )
        if (dbResponse is IOResponse.Success) {
            return R.string.album_insert_success
        } else {
            return R.string.album_insert_failure
        }
    }

    fun setAlbumTracks(album: Album): Int {
        loadingStatus.value = true
        val artistName = album.artist.name
        viewModelScope.launch {
            if (!artistName.isNullOrBlank()) {
                val response = repository.getAlbumTrack(artistName)
                if (response is IOResponse.Success) {
                    messageId =
                        insertAlbumsWithTracks(album, response.data?.tracks?.track ?: emptyList())
                } else {
                    messageId = runTimeExceptionParser((response as IOResponse.Error).exception)
                }
            }
        }
        loadingStatus.value = false
        return messageId
    }


}