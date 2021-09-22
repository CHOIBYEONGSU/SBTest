package com.chois.spoontest.api

import com.chois.test.global.Constants.Companion.SEARCH_NETWORK_CONNECT_TIME_OUT
import com.chois.test.global.Constants.Companion.SEARCH_NETWORK_READ_TIME_OUT
import com.chois.test.global.Constants.Companion.SEARCH_NETWORK_WRITE_TIME_OUT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(SEARCH_NETWORK_CONNECT_TIME_OUT, TimeUnit.MINUTES)
        .readTimeout(SEARCH_NETWORK_READ_TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(SEARCH_NETWORK_WRITE_TIME_OUT, TimeUnit.SECONDS)
        .build()

    fun getClient(baseUrl: String): Retrofit? =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}