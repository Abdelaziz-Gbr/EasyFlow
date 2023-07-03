package com.easyflow.network

import com.easyflow.utils.Constants.serverBaseURL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Network{
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpBuilder = OkHttpClient.Builder()
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        .addInterceptor(logging)
    private val client = okHttpBuilder.build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(serverBaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val easyFlowServices: EasyFlowServices by lazy {
        retrofit.create(EasyFlowServices::class.java)
    }
}