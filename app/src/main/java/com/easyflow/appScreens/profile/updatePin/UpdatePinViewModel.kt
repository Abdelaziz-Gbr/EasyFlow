package com.easyflow.appScreens.profile.updatePin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import kotlinx.coroutines.launch

class UpdatePinViewModel: ViewModel() {
    private val _respone = MutableLiveData<String?>()
    val response : LiveData<String?>
        get() = _respone
    fun updatePin(newPin: String){
        viewModelScope.launch{
            try{
            val res = Network.easyFlowServices.setPin(newPin)
                _respone.value = res.body()!!.message
            }
            catch (e : Exception){
                _respone.value = "an error occured please try again."
            }
        }

    }

    fun onMsgRecieved(){
        _respone.value = null
    }
}