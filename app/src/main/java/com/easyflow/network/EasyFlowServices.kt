package com.easyflow.network

import com.easyflow.cache.UserCache
import com.easyflow.cache.UserKey
import com.easyflow.network.models.*
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
    suspend fun getAllTickets(@Header("Authorization") authKey: String): Response<List<TicketNetworkModel>>

    @GET("passenger/Trips")
    suspend fun getTrips(@Header("Authorization") authKey: String): Response<List<TripNetworkModel>>
    @GET("plans")
    suspend fun getAllPlans(@Header("Authorization") authKey: String? = UserKey.value): List<PlanNetworkModel>

    @POST("passenger/subscribe")
    suspend fun subscribeToPlan(
        @Header("Authorization") authKey: String? = UserKey.value,
        @Body planSubscriptionModel: PlanSubscriptionModel
    ): Response<ResponseBody>

}