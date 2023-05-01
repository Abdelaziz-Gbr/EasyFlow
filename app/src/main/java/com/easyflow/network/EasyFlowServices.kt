package com.easyflow.network

import com.easyflow.network.models.TicketNetworkModel
import com.easyflow.network.models.UserNetworkModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface EasyFlowServices {

    @POST("login")
    suspend fun signIn(@Body userInfo: UserNetworkModel) : Response<ResponseBody>

    @POST("Register")
    suspend fun register(@Body user: UserNetworkModel) : Response<ResponseBody>

    @GET("passenger/profile")
    suspend fun getUserInfo(@Header("Authorization") authKey : String) : Response<UserNetworkModel>

    @PUT("passenger/recharge/{amount}")
    suspend fun recharge(@Path("amount") amount: Float,
                         @Header("Authorization") authKey: String): Response<ResponseBody>

    @GET("passenger/history")
    suspend fun getAllTickets(): Response<List<TicketNetworkModel>>

}