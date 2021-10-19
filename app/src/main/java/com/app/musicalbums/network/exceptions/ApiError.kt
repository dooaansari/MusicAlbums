package com.app.musicalbums.network.exceptions

class ApiError(message: String) : Exception(message){
}

open class NetworkException(error: Throwable): RuntimeException(error)

class NoInternetException(error: Throwable): NetworkException(error)

class ServerNotAvailableException(error: Throwable): NetworkException(error)

class HttpFailureException(error: Throwable): NetworkException(error)

class IOErrorException(error: Throwable): NetworkException(error)