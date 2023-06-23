package com.easyflow.activities.signIn.fragmentRegister

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.network.models.ServerResponse
import com.easyflow.network.models.UserNetworkModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {
    private val user = UserNetworkModel()

    private val _registerResponse = MutableLiveData<RegisterStatus?>()
    val registerResponse : LiveData<RegisterStatus?>
        get() = _registerResponse

    private val _registerErrorMessage = MutableLiveData<String?>()
        val registerErrorMessage : LiveData<String?>
            get() = _registerErrorMessage

    fun register(user: UserNetworkModel){
        viewModelScope.launch {
            _registerResponse.value = RegisterStatus.LOADING
            try {
                val registerRequest = Network.easyFlowServices.register(user)
                if(registerRequest.isSuccessful){
                    _registerResponse.value = RegisterStatus.OK
                }
                else{
                    _registerResponse.value = RegisterStatus.REGISTER_ERROR
                    _registerErrorMessage.value = Gson().fromJson(registerRequest.errorBody()?.charStream(), ServerResponse::class.java).message
                }
            }
            catch (e: Exception){
                _registerResponse.value = RegisterStatus.SERVER_ERROR
                _registerErrorMessage.value = "Please try again later."
            }
        }
    }
    fun onRegistered(){
        _registerResponse.value = null
        _registerErrorMessage.value = null
    }
}