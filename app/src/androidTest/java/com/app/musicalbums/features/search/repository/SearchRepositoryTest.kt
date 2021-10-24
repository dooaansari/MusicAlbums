package com.app.musicalbums.features.search.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.app.musicalbums.TestCoroutineRule
import com.app.musicalbums.base.BasePagingAdapter
import com.app.musicalbums.base.BasePagingDataAdapter
import com.app.musicalbums.features.main.repository.MainRepository
import com.app.musicalbums.features.main.repository.MainRepositoryImpl
import com.app.musicalbums.helpers.getNextPage
import com.app.musicalbums.models.Artist
import com.app.musicalbums.models.ArtistSearchResponse
import com.app.musicalbums.network.apis.IOResponse
import com.app.musicalbums.network.apis.LastFMService
import com.app.musicalbums.network.exceptions.OperationFailed
import com.app.musicalbums.network.exceptions.TemporaryError
import com.app.musicalbums.room.dao.AlbumDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.Response

@ExperimentalCoroutinesApi
@HiltAndroidTest
class SearchRepositoryTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltrule = HiltAndroidRule(this)

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private val service = Mockito.mock(LastFMService::class.java)

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    lateinit var repository: SearchRepository
    lateinit var artistDataSource: ArtistDataSource

    @Before
    fun setUp() {
        hiltrule.inject()
        repository = SearchRepositoryImpl(service)
        artistDataSource = ArtistDataSource(service, "query")
    }

    @Test
    fun artistLoadDataError() {
        val error = RuntimeException()
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(artistDataSource.callAPI(0))
                .thenThrow(error)
            val expectedResult = PagingSource.LoadResult.Error<Int, Artist>(error)
            val result = artistDataSource.load(
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
            Mockito.`when`(artistDataSource.callAPI(0))
                .thenReturn(
                    Response.error(
                        400,
                        "{\"message\":[\"invalid argument\"]}"
                            .toResponseBody("application/json".toMediaTypeOrNull())
                    )
                )
            val expectedResult = PagingSource.LoadResult.Error<Int, Artist>(error)
            val result = artistDataSource.load(
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
            Mockito.`when`(artistDataSource.callAPI(0))
                .thenReturn(
                    Response.success(ArtistSearchResponse())
                )
            val expectedResult = PagingSource.LoadResult.Page(
                data = listOf(),
                prevKey = -1,
                nextKey = null
            )
            val result = artistDataSource.load(
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
}