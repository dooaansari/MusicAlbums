package com.app.musicalbums.features.details

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.app.musicalbums.R
import com.app.musicalbums.TestCoroutineRule
import com.app.musicalbums.launchFragmentHiltContainer
import com.app.musicalbums.models.Track
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
class AlbumDetailsFragmentTest {

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
    fun verifyDefaultViewAppearsNoTracks() {
        launchFragmentHiltContainer<AlbumDetailsFragment>(args = Bundle().apply {
            putString("artist", "cherry")
            putString("album","album")
            putString("album_image","http://test.com")
            putParcelableArray("tracks", arrayOf())
        })
        onView(withId(R.id.image)).check(matches(isDisplayed()))
        onView(withId(R.id.artist_name)).check(matches(isDisplayed()))
        onView(withId(R.id.no_tracks)).check(matches(isDisplayed()))
        onView(withId(R.id.tracks)).check(matches(not(isDisplayed())))
    }

    @Test
    fun verifyDefaultViewAppearsTracks() {
        launchFragmentHiltContainer<AlbumDetailsFragment>(args = Bundle().apply {
            putString("artist", "cherry")
            putString("album","album")
            putString("album_image","http://test.com")
            putParcelableArray("tracks", arrayOf(Track()))
        })
        onView(withId(R.id.image)).check(matches(isDisplayed()))
        onView(withId(R.id.artist_name)).check(matches(isDisplayed()))
        onView(withId(R.id.no_tracks)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tracks)).check(matches(isDisplayed()))
    }

}