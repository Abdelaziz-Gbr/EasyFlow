package com.easyflow.api

import com.easyflow.utils.Constants.serverBaseURL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance{
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpBuilder = OkHttpClient.Builder().addInterceptor(logging)
    private val client = okHttpBuilder.build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(serverBaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: RestApi by lazy {
        retrofit.create(RestApi::class.java)
    }
}