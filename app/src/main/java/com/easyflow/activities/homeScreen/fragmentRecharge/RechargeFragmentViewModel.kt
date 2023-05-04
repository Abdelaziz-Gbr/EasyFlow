package com.easyflow.activities.homeScreen.fragmentRecharge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.cache.UserCache
import com.easyflow.cache.UserKey
import kotlinx.coroutines.launch

class RechargeFragmentViewModel: ViewModel() {
    private val _rechargeStatus = MutableLiveData<Boolean?>()
    val rechargeStatus : LiveData<Boolean?>
        get() = _rechargeStatus
    fun recharge(amount: Float){
        viewModelScope.launch {
            try {
                val rechargeResponse = Network.easyFlowServices.recharge(amount, UserKey.value!!)
                if(rechargeResponse.isSuccessful)
                    UserCache.addBalance(amount)
                _rechargeStatus.value = rechargeResponse.isSuccessful
            }
            catch (e: Exception){
                _rechargeStatus.value = false
            }
        }
    }
    fun onRechargeStatusReceived(){
        _rechargeStatus.value = null
    }
}