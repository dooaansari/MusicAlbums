package com.app.musicalbums.features.main

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.app.musicalbums.R
import com.app.musicalbums.TestCoroutineRule
import com.app.musicalbums.launchFragmentHiltContainer
import com.app.musicalbums.models.Album
import com.app.musicalbums.models.Artist
import com.app.musicalbums.room.entities.AlbumWithTracks
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock


@HiltAndroidTest
@ExperimentalCoroutinesApi
class MainFragmentTest {

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
    fun verifySearchButton() {
        launchFragmentHiltContainer<MainFragment>()
        onView(withId(R.id.action_search)).check(matches(isDisplayed()))
    }

    @Test
    fun verifySearchButtonClick() {
        val controller = mock(NavController::class.java)
        launchFragmentHiltContainer<MainFragment>(){
            Navigation.setViewNavController(requireView(),controller)
        }
        onView(withId(R.id.action_search)).perform(click())
        Mockito.verify(controller).navigate(MainFragmentDirections.actionMainToSearch())
    }

    @Test
    fun verifyNoFavouriteTextAppears() {
        launchFragmentHiltContainer<MainFragment>(){
            testCoroutineRule.runBlockingTest {
                favouriteAdapter.submitData(
                    PagingData.from(emptyList()))
            }
        }
        onView(withId(R.id.loader_main)).check(matches(not(isDisplayed())))
        onView(withId(R.id.no_data)).check(matches(isDisplayed()))
        onView(withId(R.id.favourite_albums_recyclerview)).check(matches(not(isDisplayed())))

    }

    @Test
    fun verifyNoFavouriteListAppears() {
        launchFragmentHiltContainer<MainFragment>(){
            testCoroutineRule.runBlockingTest {
                favouriteAdapter.submitData(
                    PagingData.from(listOf(AlbumWithTracks(
                        album = Album(),
                        artist = Artist(),
                        tracks = listOf(),
                        image = listOf()
                    ))))
            }
        }
        onView(withId(R.id.loader_main)).check(matches(not(isDisplayed())))
        onView(withId(R.id.no_data)).check(matches(not(isDisplayed())))
        onView(withId(R.id.favourite_albums_recyclerview)).check(matches(isDisplayed()))

    }

    @Test
    fun verifyErrorViewShows(){
        val exception = Exception()
        val loadState = CombinedLoadStates(
            refresh = LoadState.NotLoading(false),
            prepend = LoadState.Error(exception),
            append = LoadState.Error(exception),
            source = LoadStates(LoadState.Error(exception),LoadState.Error(exception),LoadState.Error(exception))
        )
        launchFragmentHiltContainer<MainFragment>(){
            this.setLoadState(loadState)
        }
        onView(withId(R.id.loader_main)).check(matches(not(isDisplayed())))
        onView(withId(R.id.no_data)).check(matches(isDisplayed()))
        onView(withId(R.id.favourite_albums_recyclerview)).check(matches(not(isDisplayed())))
    }
}

