package com.app.musicalbums.network.apis

import com.app.musicalbums.models.todos.ToDoResponse
import retrofit2.Response
import retrofit2.http.GET

interface CommentsService {

    @GET("todos")
    suspend fun getTodos(): Response<List<ToDoResponse>>
}