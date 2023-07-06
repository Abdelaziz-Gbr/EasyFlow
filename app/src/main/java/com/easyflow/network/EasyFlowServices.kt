package com.easyflow.network

import com.easyflow.cache.UserKey
import com.easyflow.network.models.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface EasyFlowServices {

    @POST("login")
    suspend fun signIn(
        @Body userInfo: UserNetworkModel) : Response<ResponseBody>

    @POST("Register")
    suspend fun register(
        @Body user: UserNetworkModel) : Response<ResponseBody>

    @GET("passenger/profile")
    suspend fun getUserInfo(
        @Header("Authorization") authKey : String? = UserKey.value) : Response<UserNetworkModel>

    @PUT("passenger/recharge/{amount}")
    suspend fun recharge(
        @Path("amount") amount: Float,
        @Header("Authorization") authKey: String): Response<ResponseBody>

    @GET("passenger/history")
    suspend fun getAllTickets(
        @Header("Authorization") authKey: String): Response<List<TicketNetworkModel>>

    @GET("passenger/Trips")
    suspend fun getTrips(
        @Header("Authorization") authKey: String): Response<List<TripNetworkModel>>
    @GET("plans")
    suspend fun getAllPlans(
        @Header("Authorization") authKey: String? = UserKey.value): List<PlanNetworkModel>

    @POST("passenger/subscribe")
    suspend fun subscribeToPlan(
        @Header("Authorization") authKey: String? = UserKey.value,
        @Body planSubscriptionModel: PlanSubscriptionModel
    ): Response<ServerResponse>

    @GET("passenger/subscriptions")
    suspend fun getUserSubscriptions(
        @Header("Authorization") authKey: String? = UserKey.value): Response<List<UserPlan>>

    @PUT("passenger/updateProfile")
    suspend fun updateUserProfile(
        @Header("Authorization") authKey: String? = UserKey.value,
        @Body profileUpdateNetworkModel: ProfileUpdateNetworkModel
    ):Response<ServerResponse>

    @POST("reset")
    suspend fun sendResetPasswordRequest(@Body email: UserNetworkModel):Response<ResponseBody>

    @PUT("passenger/subscription/repurchase/{owner-name}/{plan-name}")
    suspend fun reverseSubscriptionRepurchase(
        @Path("owner-name") ownerName: String,
        @Path("plan-name") planName: String,
        @Header("Authorization") authKey: String? = UserKey.value
    ):Response<ServerResponse>

    @PUT("passenger/set-pin")
    suspend fun setPin(
        @Body pin: String,
        @Header("Authorization") authKey: String? = UserKey.value):Response<ServerResponse>

    @PUT("passenger/password")
    suspend fun updatePassword(
        @Body updatePasswordModel: UpdatePasswordModel,
        @Header("Authorization") authKey: String? = UserKey.value):Response<ServerResponse>
    @PUT("passenger/subscription/repurchase/{owner-name}/{plan-name}")
    suspend fun reNewPlan(
        @Path("owner-name") ownerName: String,
        @Path("plan-name") planName: String,
        @Header("Authorization") authKey: String? = UserKey.value
    ):Response<ServerResponse>
}