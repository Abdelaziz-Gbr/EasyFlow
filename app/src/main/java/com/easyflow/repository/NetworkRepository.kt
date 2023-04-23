package com.easyflow.repository

import com.easyflow.models.User
import com.easyflow.api.EasyFlowServices
import okhttp3.ResponseBody
import retrofit2.Response

class NetworkRepository {
    suspend fun signIn(user: User): Response<ResponseBody>{
        return EasyFlowServices.api.signIn(user)
    }

    suspend fun getUserInfo(authKey : String): Response<User>{
        return EasyFlowServices.api.getUserInfo(authKey)
    }
    suspend fun register(user: User): Response<ResponseBody>{
        return EasyFlowServices.api.register(user)
    }
}