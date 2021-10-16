package com.app.musicalbums.models.todos

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ToDoResponse {

    class ToDo{
        @SerializedName("userId")
        @Expose
        private val userId: Int? = null

        @SerializedName("id")
        @Expose
        private val id: Int? = null

        @SerializedName("title")
        @Expose
        private val title: String? = null

        @SerializedName("completed")
        @Expose
        private val completed: Boolean? = null
    }


}