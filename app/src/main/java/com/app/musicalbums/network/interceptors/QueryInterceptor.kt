package com.app.musicalbums.network.interceptors

import com.app.musicalbums.features.search.SearchViewModel.Companion.PAGE_SIZE
import com.app.musicalbums.network.constants.InterceptorQueryParams
import com.app.musicalbums.network.constants.InterceptorQueryParams.API_KEY
import com.app.musicalbums.network.constants.InterceptorQueryParams.API_VALUE
import com.app.musicalbums.network.constants.InterceptorQueryParams.FORMAT_KEY
import com.app.musicalbums.network.constants.InterceptorQueryParams.FORMAT_VALUE
import com.app.musicalbums.network.constants.InterceptorQueryParams.LIMIT
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class QueryInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response  {
        val url = chain.request().url.newBuilder()
            .addQueryParameter(FORMAT_KEY, FORMAT_VALUE)
            .addQueryParameter(API_KEY, API_VALUE)
            .addQueryParameter(LIMIT,PAGE_SIZE.toString())
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}