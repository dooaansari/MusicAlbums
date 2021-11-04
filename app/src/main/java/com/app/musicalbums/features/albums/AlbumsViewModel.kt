package com.app.musicalbums.features.albums

import androidx.lifecycle.*
import androidx.paging.*
import com.app.musicalbums.R
import com.app.musicalbums.di.IoDispatcher
import com.app.musicalbums.features.albums.repository.AlbumsRepository
import com.app.musicalbums.models.Album
import com.app.musicalbums.models.Artist
import com.app.musicalbums.models.Track
import com.app.musicalbums.network.apis.IOResponse
import com.app.musicalbums.network.exceptions.runTimeExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AlbumsViewModel @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    val repository: AlbumsRepository,
    val state: SavedStateHandle
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 50
    }

    var clickedPosition = 0
    val loadingStatus = MutableLiveData<Boolean>()
    val favouriteAddResult = MutableLiveData<Pair<Boolean, Int>>()
    val albumTracks = MutableLiveData<List<Track>>()

    var isInitialLoad = true
    var isEmptyList = true
    var navigatedToDetails = true

    open val getTopAlbums: (artist: String?) -> LiveData<PagingData<Album>>? = { artist ->
        if (!artist.isNullOrBlank()) {
            isInitialLoad = false
            Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                val data = repository.getAlbumsListDataSource(artist.trim())
                data
            }.liveData.map { it.filter { it.name.isNotBlank() } }.cachedIn(viewModelScope)
        } else null
    }

    var topAlbumsData = getTopAlbums(state.get<String>("artist"))

    suspend fun insertAlbumsWithTracks(album: Album, tracks: List<Track>): Int {
        album.artist?.let {
            val dbResponse = repository.insertAlbumWithTracks(
                it,
                album,
                tracks
            )
            if (dbResponse is IOResponse.Success) {
                return R.string.album_insert_success
            } else {
                return R.string.album_insert_failure
            }
        } ?: return R.string.album_insert_failure
    }

    fun setAlbumTracks(album: Album) {
        loadingStatus.value = true
        val artistName = album.artist?.name
        val albumName = album.name
        viewModelScope.launch {
            if (!artistName.isNullOrBlank() && albumName.isNotBlank()) {
                val response = repository.getAlbumTrack(artistName, albumName)
                if (response is IOResponse.Success) {
                    val messageId =
                        insertAlbumsWithTracks(album, response.data?.tracks?.track ?: emptyList())
                    favouriteAddResult.value = Pair(true, messageId)
                    loadingStatus.value = false
                } else {
                    val messageId = runTimeExceptionParser((response as IOResponse.Error).exception)
                    favouriteAddResult.value = Pair(false, messageId)
                    loadingStatus.value = false
                }
            } else {
                loadingStatus.value = false
            }
        }

    }

    fun getAlbumTracks(artist: String?, album: String?) {
        loadingStatus.value = true
        if (!artist.isNullOrBlank() && !album.isNullOrBlank()) {
            viewModelScope.launch {
                val response = repository.getAlbumTrack(artist, album)
                if (response is IOResponse.Success) {
                    albumTracks.value = response.data?.tracks?.track ?: emptyList()
                    loadingStatus.value = false
                } else {
                    albumTracks.value = emptyList()
                    loadingStatus.value = false
                }
            }
        } else {
            loadingStatus.value = false
        }

    }

    fun resetObserver() {
        favouriteAddResult.value = Pair(false, R.string.album_insert_failure)
    }

}