package com.easyflow.appScreens.services.fragmentUserSubscription.planManagment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.network.models.UserPlan
import com.easyflow.network.models.getRepurchasReveresed
import com.easyflow.utils.PlanChanged
import com.easyflow.utils.StatusCode
import kotlinx.coroutines.launch

class SubManagmentViewModel: ViewModel() {

    private val _reSubMsg = MutableLiveData<String?>()
    val reSubMsg : LiveData<String?>
        get() = _reSubMsg

    private val _rePurMsg = MutableLiveData<Boolean?>()
    val rePurMsg : LiveData<Boolean?>
        get() = _rePurMsg

    val subscription = MutableLiveData<UserPlan>()

    fun setUserSubscription(userPlan: UserPlan){
        subscription.value = userPlan
    }
    /*fun onSwitchCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        reversePlanSub()
    }*/
    fun reversePlanSub(){
        viewModelScope.launch {
            try {
                val userPlan = subscription.value
                val res = Network.easyFlowServices.reverseSubscriptionRepurchase(
                    ownerName = userPlan!!.planOwnerName,
                    planName = userPlan.planName
                )
                if(res.isSuccessful && res.body()!!.status == StatusCode.from(200).name){
                    PlanChanged.plan.value = userPlan.getRepurchasReveresed()
                    _rePurMsg.value = true
                }
                else{
                    _rePurMsg.value = false
                }
            }
            catch (e: Exception){
                _rePurMsg.value = false
            }
        }
    }

    fun onResubMsgReceived() {
        _reSubMsg.value = null
    }

    fun renewSubscription() {
        viewModelScope.launch {
            val currentPlan = subscription.value
            try{
                val renewResponse = Network.easyFlowServices.reNewPlan(
                    currentPlan!!.planOwnerName,
                    currentPlan.planName
                )
                _reSubMsg.value = renewResponse.body()!!.message
            }
            catch (e: Exception){
                _reSubMsg.value = "sorry something went wrong please try again later."
            }
        }
    }

    fun onRepurchasedReceived() {
        _rePurMsg.value = null
    }
}