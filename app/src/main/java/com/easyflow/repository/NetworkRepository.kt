package com.easyflow.repository

import com.easyflow.models.User
import com.easyflow.api.Network
import okhttp3.ResponseBody
import retrofit2.Response

class NetworkRepository {
    suspend fun signIn(user: User): Response<ResponseBody>{
        return Network.easyFlowServices.signIn(user)
    }

    suspend fun getUserInfo(authKey : String): Response<User>{
        return Network.easyFlowServices.getUserInfo(authKey)
    }
    suspend fun register(user: User): Response<ResponseBody>{
        return Network.easyFlowServices.register(user)
    }
}