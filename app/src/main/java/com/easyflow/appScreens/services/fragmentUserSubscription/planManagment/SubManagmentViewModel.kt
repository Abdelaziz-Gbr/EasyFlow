package com.easyflow.appScreens.services.fragmentUserSubscription.planManagment

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.network.models.UserPlan
import kotlinx.coroutines.launch

class SubManagmentViewModel: ViewModel() {

    private val _msg = MutableLiveData<String?>()
    val msg : LiveData<String?>
        get() = _msg

    val subscription = MutableLiveData<UserPlan>()

    fun setUserSubscription(userPlan: UserPlan){
        subscription.value = userPlan
    }
    fun onSwitchCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        reversePlanSub()
    }
    private fun reversePlanSub(){
        viewModelScope.launch {
            try {
                val userPlan = subscription.value
                val res = Network.easyFlowServices.reverseSubscriptionRepurchase(
                    ownerName = userPlan!!.planOwnerName,
                    planName = userPlan.planName
                )
                _msg.value = res.body()!!.message
                subscription.value?.repurchase = !(subscription.value?.repurchase)!!
            }
            catch (e: Exception){
                _msg.value = "sorry something went wrong please try again later."
            }
        }
    }

    fun onMsgRecieved() {
        _msg.value = null
    }

    fun renewSubscription() {
        viewModelScope.launch {
            val currentPlan = subscription.value
            try{
                val renewResponse = Network.easyFlowServices.reNewPlan(
                    currentPlan!!.planOwnerName,
                    currentPlan.planName
                )
                _msg.value = renewResponse.body()!!.message
            }
            catch (e: Exception){
                _msg.value = "sorry something went wrong please try again later."
            }
        }
    }
}