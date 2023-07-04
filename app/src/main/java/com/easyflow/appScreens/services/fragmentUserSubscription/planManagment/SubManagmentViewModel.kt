package com.easyflow.appScreens.services.fragmentUserSubscription.planManagment

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.network.models.UserPlan
import com.easyflow.utils.subscribeToMainFeed
import com.easyflow.utils.unSubscribeToMainFeed
import kotlinx.coroutines.launch

class SubManagmentViewModel: ViewModel() {

    private val _error = MutableLiveData<Boolean>()
    val error : LiveData<Boolean>
        get() = _error

    val subscription = MutableLiveData<UserPlan>()

    fun setUserSubscription(userPlan: UserPlan){
        subscription.value = userPlan
    }
    fun onSwitchCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        reversePlanSub()
    }
    fun reversePlanSub(){
        viewModelScope.launch {
            try {
                val userPlan = subscription.value
                val res = Network.easyFlowServices.reverseSubscriptionRepurchase(
                    ownerName = userPlan!!.planOwnerName,
                    planName = userPlan!!.planName
                )
                if(!res.isSuccessful){
                    _error.value = true
                }
            }
            catch (e: Exception){
                _error.value = true
            }
        }
    }

    fun onErrorRecieved() {
        _error.value = false
    }
}