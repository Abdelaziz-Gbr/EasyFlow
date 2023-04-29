package com.easyflow.activities.homeScreen.fragmentRecharge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.api.Network
import com.easyflow.cache.UserCache
import kotlinx.coroutines.launch

class RechargeFragmentViewModel: ViewModel() {
    private val _rechargeStatus = MutableLiveData<Boolean?>()
    val rechargeStatus : LiveData<Boolean?>
        get() = _rechargeStatus
    fun recharge(amount: Int){
        viewModelScope.launch {
            try {
                val rechargeResponse = Network.easyFlowServices.recharge(amount, UserCache.userKey!!)
                Log.d("Recharge_Response", rechargeResponse.body().toString())
                _rechargeStatus.value = rechargeResponse.isSuccessful
            }
            catch (e: Exception){

            }
        }
    }
    fun onRechargeStatusReceived(){
        _rechargeStatus.value = null
    }
}