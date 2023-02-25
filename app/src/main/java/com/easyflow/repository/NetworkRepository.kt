package com.easyflow.repository

import com.easyflow.models.ServerResponse
import com.easyflow.models.User
import com.easyflow.api.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Response

class NetworkRepository {
    suspend fun signIn(user: User): Response<ResponseBody>{
        return RetrofitInstance.api.signIn(user)
    }

    suspend fun getUserInfo(authKey : String): Response<User>{
        return RetrofitInstance.api.getUserInfo(authKey)
    }
    suspend fun register(user: User): Response<ResponseBody>{
        return RetrofitInstance.api.register(user)
    }
}