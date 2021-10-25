package com.app.musicalbums.features.search

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.app.musicalbums.R
import com.app.musicalbums.TestCoroutineRule
import com.app.musicalbums.custom_views.SearchComponent
import com.app.musicalbums.launchFragmentHiltContainer
import com.app.musicalbums.models.Artist
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
import java.lang.Exception


@HiltAndroidTest
@ExperimentalCoroutinesApi
class SearchFragmentTest {

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
    fun verifySearchComponent(){
        launchFragmentHiltContainer<SearchFragment>()
        onView(withId(R.id.search_button)).check(matches(isDisplayed()))
        onView(withId(R.id.search_field)).check(matches(isDisplayed()))
    }

    @Test
    fun verifySearchButtonDisabledWithNoText(){
        launchFragmentHiltContainer<SearchFragment>()
        onView(withId(R.id.search_button)).check(matches(not(isEnabled())))
    }

    @Test
    fun verifySearchButtonEnableddWithValidText(){
        launchFragmentHiltContainer<SearchFragment>()
        onView(withId(R.id.search_text_field)).perform(typeText("Cherry"))
        onView(withId(R.id.search_button)).check(matches(isEnabled()))
    }

    @Test
    fun verifySearchButtonDisabledWithInValidText(){
        launchFragmentHiltContainer<SearchFragment>()
        onView(withId(R.id.search_text_field)).perform(typeText("Cherry&"))
        onView(withId(R.id.search_button)).check(matches(not(isEnabled())))
    }

    @Test
    fun verifyInitialStateOfRecycler(){
        launchFragmentHiltContainer<SearchFragment>()
        onView(withId(R.id.loader)).check(matches(not(isDisplayed())))
        onView(withId(R.id.no_data)).check(matches(not(isDisplayed())))
        onView(withId(R.id.artist_recyclerview)).check(matches(isDisplayed()))
    }

    @Test
    fun verifyRecyclerWithAdapterData(){
        launchFragmentHiltContainer<SearchFragment>(){
            testCoroutineRule.runBlockingTest {
                artistAdapter.submitData(PagingData.from(listOf<Artist>(
                    Artist("Dooa Ansari")
                )))
            }
        }
        onView(withId(R.id.loader)).check(matches(not(isDisplayed())))
        onView(withId(R.id.no_data)).check(matches(not(isDisplayed())))
        onView(withId(R.id.artist_recyclerview)).check(matches(isDisplayed()))
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
        launchFragmentHiltContainer<SearchFragment>(){
            this.setLoadState(loadState,false)
        }
        onView(withId(R.id.loader)).check(matches(not(isDisplayed())))
        onView(withId(R.id.no_data)).check(matches(isDisplayed()))
        onView(withId(R.id.artist_recyclerview)).check(matches(not(isDisplayed())))
    }

    @Test
    fun verifyLoaderViewShows(){
        val loadState = CombinedLoadStates(
            refresh = LoadState.Loading,
            prepend = LoadState.Loading,
            append = LoadState.Loading,
            source = LoadStates(LoadState.Loading,LoadState.Loading,LoadState.Loading)
        )
        launchFragmentHiltContainer<SearchFragment>(){
            this.setLoadState(loadState,false)
        }
        onView(withId(R.id.loader)).check(matches(isDisplayed()))
        onView(withId(R.id.no_data)).check(matches(not(isDisplayed())))
        onView(withId(R.id.artist_recyclerview)).check(matches(not(isDisplayed())))
    }

    @Test
    fun verifySearchOnClick(){
        val component = mock(SearchComponent::class.java)
        component.searchButtonClick =  {}
        launchFragmentHiltContainer<SearchFragment>(){
        }
        onView(withId(R.id.search_text_field)).perform(typeText("Cherry"))
        onView(withId(R.id.search_button)).perform(click())
        Mockito.verify(component).searchButtonClick("Cherry")
    }
}

