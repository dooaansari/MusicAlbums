package com.app.musicalbums.features.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.musicalbums.TestCoroutineRule
import com.app.musicalbums.features.search.repository.SearchRepository
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
class SearchViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltrule = HiltAndroidRule(this)

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private val repository = Mockito.mock(SearchRepository::class.java)

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        hiltrule.inject()
        viewModel = SearchViewModel(testCoroutineDispatcher, repository)
    }

    @Test
    fun getSearchedArtistNullQuery() {
        val query = null
        assertEquals(viewModel.getSearchedArtist(query), null)
    }

    @Test
    fun getSearchedArtist() {
        val query = "query"
        val spy = Mockito.spy(viewModel)
        spy.getSearchedArtist(query)
        Mockito.verify(spy).repository.getArtistListDataSource(query)
    }

}