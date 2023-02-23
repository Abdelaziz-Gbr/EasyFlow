package com.easyflow.network.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder{
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpBuilder = OkHttpClient.Builder().addInterceptor(logging)
    private val client = okHttpBuilder.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.102:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service : Class<T>) : T{
        return retrofit.create(service)
    }
}