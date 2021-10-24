package com.app.musicalbums.features.main.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.musicalbums.TestCoroutineRule
import com.app.musicalbums.network.apis.IOResponse
import com.app.musicalbums.room.dao.AlbumDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainRepositoryTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltrule = HiltAndroidRule(this)

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private val dao = Mockito.mock(AlbumDao::class.java)

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    lateinit var repository: MainRepository

    @Before
    fun setUp() {
        hiltrule.inject()
        repository = MainRepositoryImpl(dao)
    }


    @Test
    fun deleteAlbumError() {
        val albumName = "test"
        val ex = RuntimeException()
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(dao.deleteAlbum(albumName)).thenThrow(ex)
            assertEquals(repository.deleteAlbum(albumName), IOResponse.Error(ex))

        }
    }

    @Test
    fun deleteAlbumSuccess() {
        val albumName = "test"
        testCoroutineRule.runBlockingTest {
            assertEquals(repository.deleteAlbum(albumName), IOResponse.Success(true))
        }
    }

    @Test
    fun getFavouriteAlbums() {
        testCoroutineRule.runBlockingTest {
            val spy = Mockito.spy(repository)
            spy.getFavouritesDataSource()
            Mockito.verify(spy).albumDao.getAllAlbums()
        }

    }

}