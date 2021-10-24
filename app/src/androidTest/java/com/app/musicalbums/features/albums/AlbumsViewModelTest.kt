package com.app.musicalbums.features.albums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.musicalbums.R
import com.app.musicalbums.TestCoroutineRule
import com.app.musicalbums.features.albums.repository.AlbumsRepository
import com.app.musicalbums.models.*
import com.app.musicalbums.network.apis.IOResponse
import com.app.musicalbums.room.entities.AlbumWithTracks
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Response

@ExperimentalCoroutinesApi
@HiltAndroidTest
class AlbumsViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltrule = HiltAndroidRule(this)

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private val repository = mock(AlbumsRepository::class.java)

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: AlbumsViewModel

    @Before
    fun setUp() {
        hiltrule.inject()
        viewModel = AlbumsViewModel(testCoroutineDispatcher, repository)
    }

    @Test
    fun getTopAlbumeNullAlbumName() {
        val albumName = null
        assertEquals(viewModel.getTopAlbums(albumName), null)
    }

    @Test
    fun getTopAlbums() {
        val albumName = "cherr"
        val spy = Mockito.spy(viewModel)
        spy.getTopAlbums(albumName)
        Mockito.verify(spy).repository.getAlbumsListDataSource(albumName)
    }

    @Test
    fun insertAlbumsWithTracksAlbumArtistNull() {
        testCoroutineRule.runBlockingTest {
            assertEquals(
                viewModel.insertAlbumsWithTracks(Album(artist = null), emptyList()),
                R.string.album_insert_failure
            )
        }
    }

    @Test
    fun insertAlbumsWithTracksAlbumError() {
        testCoroutineRule.runBlockingTest {
            val artist = Artist()
            val album = Album(artist = artist)
            val tracks = emptyList<Track>()
            val errorIO = IOResponse.Error(Exception())
            `when`(repository.insertAlbumWithTracks(artist, album, tracks)).thenReturn(errorIO)
            assertEquals(
                viewModel.insertAlbumsWithTracks(album, tracks),
                R.string.album_insert_failure
            )
        }
    }

    @Test
    fun insertAlbumsWithTracksAlbumSuccess() {
        testCoroutineRule.runBlockingTest {
            val artist = Artist()
            val album = Album(artist = artist)
            val tracks = emptyList<Track>()
            val successIO = IOResponse.Success<Long>(1)
            `when`(repository.insertAlbumWithTracks(artist, album, tracks)).thenReturn(successIO)
            assertEquals(
                viewModel.insertAlbumsWithTracks(album, tracks),
                R.string.album_insert_success
            )
        }
    }


    @Test
    fun setAlbumTracksArtistNameNull() {
        testCoroutineRule.runBlockingTest {
            viewModel.setAlbumTracks(Album(artist = Artist(name = null)))
        }
        assertEquals(viewModel.loadingStatus.value, false)
    }

    @Test
    fun setAlbumTracksError() {
        val artist = Artist(name = "artist")
        val album = Album(name = "album", artist = artist)
        val ioError = IOResponse.Error(Exception())
        testCoroutineRule.runBlockingTest {
            `when`(
                repository.getAlbumTrack(
                    album.artist?.name ?: "",
                    album.name
                )
            ).thenReturn(ioError)
            viewModel.setAlbumTracks(album)
        }
        assertEquals(viewModel.favouriteAddResult.value?.first, false)
        assertEquals(viewModel.favouriteAddResult.value?.second, R.string.unexpected_error)
        assertEquals(viewModel.loadingStatus.value, false)
    }

    @Test
    fun setAlbumTracksSuccess() {
        val artist = Artist(name = "artist")
        val album = Album(name = "album", artist = artist)
        val ioSuccess = IOResponse.Success(TrackAlbum())
        testCoroutineRule.runBlockingTest {
            `when`(
                repository.getAlbumTrack(
                    album.artist?.name ?: "",
                    album.name
                )
            ).thenReturn(ioSuccess)
            viewModel.setAlbumTracks(album)
        }
        assertEquals(viewModel.favouriteAddResult.value?.first, true)
        assertEquals(viewModel.loadingStatus.value, false)
    }

    @Test
    fun getAlbumTracksInvalidParams() {
        testCoroutineRule.runBlockingTest {
            viewModel.getAlbumTracks(null, null)
        }
        assertEquals(viewModel.loadingStatus.value, false)
    }

    @Test
    fun getAlbumTracksSuccess() {
        val artist = "artist"
        val album = "album"
        val trackList = listOf<Track>(Track())
        val trackAlbum = TrackAlbum(tracks = Tracks(track = trackList))
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.getAlbumTrack(artist, album))
                .thenReturn(
                    IOResponse.Success(trackAlbum)
                )
            viewModel.getAlbumTracks(artist, album)
        }
        assertEquals(viewModel.albumTracks.value?.size, 1)
        assertEquals(viewModel.loadingStatus.value, false)
    }

    @Test
    fun getAlbumTracksError() {
        val artist = "artist"
        val album = "album"
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.getAlbumTrack(artist, album))
                .thenReturn(
                    IOResponse.Error(Exception())
                )
            viewModel.getAlbumTracks(artist, album)
        }
        assertEquals(viewModel.albumTracks.value?.size, 0)
        assertEquals(viewModel.loadingStatus.value, false)
    }

}