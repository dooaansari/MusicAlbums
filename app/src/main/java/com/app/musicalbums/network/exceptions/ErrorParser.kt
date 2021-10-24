package com.app.musicalbums.network.exceptions

import com.app.musicalbums.R
import com.app.musicalbums.models.ResponseError
import com.app.musicalbums.network.constants.ApiErrorCodes
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import java.lang.IllegalStateException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun parseError(error: String?) : Exception{
  val gson = Gson()
  val errorModel = gson.fromJson(error, ResponseError::class.java)

  when(errorModel.code){
    ApiErrorCodes.AUTHENTICATION_FAILED -> throw AuthenticationFailed()
    ApiErrorCodes.OPERATION_FAILED -> throw OperationFailed()
    else -> throw TemporaryError()
  }
}

fun runTimeExceptionParser(ex: Throwable): Int{
  return when(ex){
    is SocketTimeoutException -> R.string.network_error
    is UnknownHostException -> R.string.server_unresponsive
    is HttpException -> R.string.unexpected_error
    is IOException -> R.string.server_unresponsive
    is AuthenticationFailed -> R.string.authentication_failed
    is IllegalStateException -> R.string.track_false
    is TemporaryError -> R.string.operation_failed

    else -> R.string.unexpected_error
  }
}