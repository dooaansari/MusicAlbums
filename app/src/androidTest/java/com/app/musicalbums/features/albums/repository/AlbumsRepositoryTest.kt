package com.app.musicalbums.features.albums.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.app.musicalbums.TestCoroutineRule
import com.app.musicalbums.models.*
import com.app.musicalbums.network.apis.IOResponse
import com.app.musicalbums.network.apis.LastFMService
import com.app.musicalbums.network.exceptions.TemporaryError
import com.app.musicalbums.room.dao.AlbumDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

@ExperimentalCoroutinesApi
@HiltAndroidTest
class AlbumsRepositoryTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltrule = HiltAndroidRule(this)

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private val service = Mockito.mock(LastFMService::class.java)

    private val dao = Mockito.mock(AlbumDao::class.java)

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    lateinit var repository: AlbumsRepository
    lateinit var albumsDataSource: AlbumsDataSource

    @Before
    fun setUp() {
        hiltrule.inject()
        repository = AlbumsRepositoryImpl(service, dao)
        albumsDataSource = AlbumsDataSource(service, "albumName")
    }

    @Test
    fun albumLoadDataError() {
        val error = RuntimeException()
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(albumsDataSource.callAPI(0))
                .thenThrow(error)
            val expectedResult = PagingSource.LoadResult.Error<Int, Album>(error)
            val result = albumsDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
            assertEquals(
                expectedResult, result
            )
        }

    }

    @Test
    fun artistLoadDataErrorApi() {
        val error = TemporaryError()
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(albumsDataSource.callAPI(0))
                .thenReturn(
                    Response.error(
                        400,
                        "{\"message\":[\"invalid argument\"]}"
                            .toResponseBody("application/json".toMediaTypeOrNull())
                    )
                )
            val expectedResult = PagingSource.LoadResult.Error<Int, Album>(error)
            val result = albumsDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
            assertEquals(
                expectedResult.throwable.localizedMessage,
                (result as PagingSource.LoadResult.Error).throwable.localizedMessage
            )
        }

    }

    @Test
    fun artistLoadDataSuccessApi() {
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(albumsDataSource.callAPI(0))
                .thenReturn(
                    Response.success(TopAlbumsResponse())
                )
            val expectedResult = PagingSource.LoadResult.Page(
                data = listOf(),
                prevKey = -1,
                nextKey = null
            )
            val result = albumsDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
            assertEquals(
                expectedResult, result
            )
        }

    }

    @Test
    fun getAlbumTrackError() {
        val albumName = "test"
        val artistName = "artistName"
        val ex = RuntimeException()
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(service.getAlbumTracks("album.getinfo", artistName, albumName))
                .thenThrow(ex)
            assertEquals(repository.getAlbumTrack(artistName, albumName), IOResponse.Error(ex))

        }
    }

    @Test
    fun getAlbumTrackApiError() {
        val error = TemporaryError()
        val albumName = "test"
        val artistName = "artistName"
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(service.getAlbumTracks("album.getinfo", artistName, albumName))
                .thenReturn(
                    Response.error(
                        400,
                        "{\"message\":[\"invalid argument\"]}"
                            .toResponseBody("application/json".toMediaTypeOrNull())
                    )
                )
            val errorResult = IOResponse.Error(error)
            val result = repository.getAlbumTrack(artistName, albumName)
            assertEquals(
                (result as IOResponse.Error).exception.localizedMessage,
                errorResult.exception.localizedMessage
            )

        }
    }

    @Test
    fun getAlbumTrackApiSuccess() {
        val albumName = "test"
        val artistName = "artistName"
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(service.getAlbumTracks("album.getinfo", artistName, albumName))
                .thenReturn(
                    Response.success(null)
                )
            val successResult = IOResponse.Success(null)
            val result = repository.getAlbumTrack(artistName, albumName)
            assertEquals(
                result, successResult
            )

        }
    }

    @Test
    fun insertAlbumWithTracksError() {
        val exception = RuntimeException()
        val artist = Artist()
        val album = Album()
        val track = listOf<Track>()
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(dao.insertAlbumsWithTracks(artist, album, track))
                .thenThrow(exception)
            assertEquals(
                repository.insertAlbumWithTracks(artist, album, track),
                IOResponse.Error(exception)
            )

        }
    }

    @Test
    fun insertAlbumWithTracks() {
        val artist = Artist()
        val album = Album()
        val track = listOf<Track>()
        val data:Long = 1
        testCoroutineRule.runBlockingTest {
            assertEquals(
                (repository.insertAlbumWithTracks(artist, album, track) as IOResponse.Success).data,
                IOResponse.Success(data).data
            )

        }
    }
}