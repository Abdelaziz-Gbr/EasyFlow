package com.easyflow.network

import com.easyflow.activities.SignInActivity
import com.easyflow.models.User
import com.easyflow.network.rest.RestApiManager

class NetworkManager {

    fun signIn(username: String, userPassword: String, signInActivity: SignInActivity){
        val apiManager = RestApiManager()
        val userInfo = User(
            null,
            userName = username,
            password = userPassword,
        )

        apiManager.signIn(userInfo, signInActivity)
    }

}
