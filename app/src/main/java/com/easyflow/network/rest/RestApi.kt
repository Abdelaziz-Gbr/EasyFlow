package com.easyflow.network.rest

import com.easyflow.models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RestApi {

    @POST("login")
    fun signIn(@Body userInfo: User) : Call<ResponseBody>
}