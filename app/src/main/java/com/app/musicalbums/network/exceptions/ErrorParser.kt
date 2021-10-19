package com.app.musicalbums.network.exceptions

import android.util.Log
import com.app.musicalbums.R
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun parseError(error: String?, httpCode: Int){

  Log.i("tag", error.toString())
  Log.i("tag", httpCode.toString())
}

fun runTimeExceptionParser(ex: Throwable): Int{
  return when(ex){
    is SocketTimeoutException -> R.string.network_error
    is UnknownHostException -> R.string.server_unresponsive
    is HttpException -> R.string.unexpected_error
    is IOException -> R.string.server_unresponsive

    else -> R.string.unexpected_error
  }
}