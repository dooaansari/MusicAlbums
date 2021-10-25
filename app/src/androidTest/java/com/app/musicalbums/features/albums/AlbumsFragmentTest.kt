package com.app.musicalbums.features.albums

import android.os.Bundle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.app.musicalbums.R
import com.app.musicalbums.TestCoroutineRule
import com.app.musicalbums.features.albums.AlbumsFragment
import com.app.musicalbums.launchFragmentHiltContainer
import com.app.musicalbums.models.Album
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@ExperimentalCoroutinesApi
class AlbumsFragmentTest {

    @get:Rule
    var hiltrule = HiltAndroidRule(this)

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()


    @Before
    fun setUp() {
        hiltrule.inject()
    }

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Test
    fun verifyLoaderAppears() {
        launchFragmentHiltContainer<AlbumsFragment>(args = Bundle().apply {
            putString("artist", "cherry")
        })
        onView(withId(R.id.loader_main)).check(matches(isDisplayed()))
    }

    @Test
    fun verifyListAppears() {
        launchFragmentHiltContainer<AlbumsFragment>(args = Bundle().apply {
            putString("artist", "cherry")
        }) {
            testCoroutineRule.runBlockingTest {
                albumsAdapter.submitData(PagingData.from(listOf(Album())))
            }
        }
        onView(withId(R.id.loader_main)).check(matches(not(isDisplayed())))
        onView(withId(R.id.albums_recyclerview)).check(matches(isDisplayed()))
    }

    @Test
    fun verifyErrorViewShows() {
        val exception = Exception()
        val loadState = CombinedLoadStates(
            refresh = LoadState.NotLoading(false),
            prepend = LoadState.Error(exception),
            append = LoadState.Error(exception),
            source = LoadStates(
                LoadState.Error(exception),
                LoadState.Error(exception),
                LoadState.Error(exception)
            )
        )
        launchFragmentHiltContainer<AlbumsFragment>(args = Bundle().apply {
            putString("artist", "cherry")
        }) {
            this.setLoadState(loadState)
        }
        onView(withId(R.id.loader_main)).check(matches(not(isDisplayed())))
        onView(withId(R.id.no_data)).check(matches(isDisplayed()))
        onView(withId(R.id.albums_recyclerview)).check(matches(not(isDisplayed())))
    }
}

