package com.app.musicalbums

import com.app.musicalbums.features.login.LoginRepository
import com.app.musicalbums.features.login.LoginViewModel
import com.app.musicalbums.models.todos.ToDoResponse
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import org.mockito.Mockito.*
import retrofit2.Response

@ExperimentalCoroutinesApi
@HiltAndroidTest
class LoginViewModelTest {
    @get:Rule
    var hiltrule = HiltAndroidRule(this)

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @Inject
    lateinit var repository: LoginRepository

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        hiltrule.inject()
    }

    @Test
    fun getComments(){
       testCoroutineRule.runBlockingTest {
           val result = Response.success(listOf<ToDoResponse>())
           Mockito.`when`(repository.getComments()).thenReturn(result)

       }
    }

    @Test
    fun test(){
      val spy = spy(LoginViewModel(testCoroutineDispatcher,repository))
      spy.test()
      verify(spy).repository.checkPrint()
    }

}