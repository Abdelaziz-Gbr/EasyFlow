package com.easyflow.api

import com.easyflow.models.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RestApi {

    @POST("login")
    suspend fun signIn(@Body userInfo: User) : Response<ResponseBody>

    @POST("Register")
    suspend fun register(@Body user: User) : Response<ResponseBody>

    @GET("passenger/profile")
    suspend fun getUserInfo(@Header("Authorization") authKey : String) : Response<User>

}