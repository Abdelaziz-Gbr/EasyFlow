package com.easyflow.activities.signIn.fragmentForgotPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.network.models.UserNetworkModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class ForgotPasswordViewModel : ViewModel() {
    private val _emailFound = MutableLiveData<String?>()
    val emailFound : LiveData<String?>
        get() = _emailFound
    fun sendUserReset(email : String){
        viewModelScope.launch {
            val req =
                Network.easyFlowServices.sendResetPasswordRequest(UserNetworkModel(email = email))
            _emailFound.value =
                if (req.isSuccessful) "check your email for the reset link." else "please check your email address again."
        }
    }

    fun onMsgRecieved() {
        _emailFound.value = null
    }
}