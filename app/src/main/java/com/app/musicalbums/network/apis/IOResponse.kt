package com.app.musicalbums.network.apis

sealed class IOResponse<out R> {
    data class Success<out T>(val data: T) : IOResponse<T>()
    data class Error(val exception: Exception) : IOResponse<Nothing>()
}