package com.easyflow.activities.signIn.fragmentRegister

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.network.models.ServerResponse
import com.easyflow.network.models.UserNetworkModel
import com.easyflow.utils.StatusCode
import com.google.gson.Gson
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {
    private val _registerResponse = MutableLiveData<ServerResponse?>()
    val registerResponse : LiveData<ServerResponse?>
        get() = _registerResponse
    fun register(user: UserNetworkModel){
        viewModelScope.launch {
            try {
                val registerRequest = Network.easyFlowServices.register(user)
                if(registerRequest.isSuccessful){
                    _registerResponse.value = registerRequest.body()
                }
                else{
                    _registerResponse.value = Gson().fromJson(registerRequest.errorBody()?.charStream(), ServerResponse::class.java)
                }
            }
            catch (e: Exception){
                _registerResponse.value = ServerResponse(StatusCode.Unknown.name,"Please try again later.")
            }
        }
    }
    fun onResponseReceived(){
        _registerResponse.value = null
    }


}