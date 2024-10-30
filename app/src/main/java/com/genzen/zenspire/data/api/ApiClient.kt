package com.genzen.zenspire.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val okHttpClient = buildOkHttpClient()
//    private const val BASE_URL = "http://10.0.2.2:3000"
    private const val BASE_URL = "https://zenspire-f5ec6.et.r.appspot.com"

    fun getApiInstance(): ApiService {
        val builder = Retrofit.Builder().baseUrl("$BASE_URL/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return builder.create(ApiService::class.java)
    }

    private fun buildOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }
}