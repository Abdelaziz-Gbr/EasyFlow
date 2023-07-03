package com.easyflow.activities.signIn.fragmentForgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.network.models.UserNetworkModel
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {
    fun sendUserReset(email : String){
        viewModelScope.launch{
            Network.easyFlowServices.sendResetPasswordRequest(UserNetworkModel(email = email))
        }
    }
}