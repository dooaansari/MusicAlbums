package com.app.musicalbums.network

import com.app.musicalbums.network.exceptions.HttpFailureException
import com.app.musicalbums.network.exceptions.IOErrorException
import com.app.musicalbums.network.exceptions.NoInternetException
import com.app.musicalbums.network.exceptions.ServerNotAvailableException
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface ResponseParser {
    fun onSuccess(parser: ()-> Unit){
        try {
           parser()
        }catch (socketTimeOutException: SocketTimeoutException){
            throw NoInternetException(socketTimeOutException)
        }catch (unknownHostException: UnknownHostException){
              throw ServerNotAvailableException(unknownHostException)
        }catch (httpException: HttpException){
              throw HttpFailureException(httpException)
        }catch (ioException: IOException){
          throw IOErrorException(ioException)
        }
    }

    fun onFailure(){

    }
}