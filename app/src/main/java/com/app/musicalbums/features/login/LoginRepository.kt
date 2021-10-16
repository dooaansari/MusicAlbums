package com.app.musicalbums.features.login

import android.util.Log
import com.app.musicalbums.base.BaseRepository
import com.app.musicalbums.models.todos.ToDoResponse
import com.app.musicalbums.network.apis.CommentsService
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val commentsService: CommentsService) : BaseRepository() {

    suspend fun getComments() :Response<List<ToDoResponse>>{
                val response = commentsService.getTodos()
                Log.i("response",response.toString())
                return response
    }

    fun checkPrint(){
        store.test()
    }
}