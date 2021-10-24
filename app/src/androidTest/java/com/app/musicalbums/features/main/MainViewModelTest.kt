package com.app.musicalbums.features.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.musicalbums.R
import com.app.musicalbums.TestCoroutineRule
import com.app.musicalbums.features.main.repository.MainRepository
import com.app.musicalbums.network.apis.IOResponse
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltrule = HiltAndroidRule(this)

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private val repository = mock(MainRepository::class.java)

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        hiltrule.inject()
        viewModel = MainViewModel(testCoroutineDispatcher, repository)
    }


    @Test
    fun deleteAlbumAlbumNameNull() {
        testCoroutineRule.runBlockingTest {
            viewModel.deleteAlbum(null)
        }
        assertEquals(viewModel.deleteMessageId.value?.first, false)
        assertEquals(viewModel.deleteMessageId.value?.second, R.string.delete_album_error)
    }


    @Test
    fun deleteAlbumAlbumNameBlank() {
        val viewModel = MainViewModel(testCoroutineDispatcher, repository)
        testCoroutineRule.runBlockingTest {
            viewModel.deleteAlbum("")
        }
        assertEquals(viewModel.deleteMessageId.value?.first, false)
        assertEquals(viewModel.deleteMessageId.value?.second, R.string.delete_album_error)
    }

    @Test
    fun deleteAlbumAlbumNameValidDeleteError() {
        val albumName = "test"
        val errorIO = IOResponse.Error(Exception())
        testCoroutineRule.runBlockingTest {
            `when`(repository.deleteAlbum(albumName)).thenReturn(errorIO)
            viewModel.deleteAlbum(albumName)
        }
        assertEquals(viewModel.deleteMessageId.value?.first, false)
        assertEquals(viewModel.deleteMessageId.value?.second, R.string.delete_album_error)
    }

    @Test
    fun deleteAlbumAlbumNameValidDeleteSuccess() {
        val albumName = "test"
        val successIO = IOResponse.Success(true)
        testCoroutineRule.runBlockingTest {
            `when`(repository.deleteAlbum(albumName)).thenReturn(successIO)
            viewModel.deleteAlbum(albumName)
        }
        assertEquals(viewModel.deleteMessageId.value?.first, true)
        assertEquals(viewModel.deleteMessageId.value?.second, R.string.album_delete_success)
    }

    @Test
    fun getFavouriteAlbums() {
        val spy = spy(viewModel)
        spy.getFavouriteAlbums()
        verify(spy).repository.getFavouritesDataSource()
    }

}