package com.easyflow.appScreens.services.fragmentPlans.planDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.network.models.PlanNetworkModel
import com.easyflow.network.models.PlanSubscriptionModel
import com.easyflow.utils.ApiCallStatus
import kotlinx.coroutines.launch


class PlanDetailsFragmentViewModel : ViewModel() {
    private val _subscribeCallStatus = MutableLiveData<ApiCallStatus?>()
    val subscribeCallStatus : LiveData<ApiCallStatus?>
        get() = _subscribeCallStatus
    fun subscripeToPlan(plan : PlanNetworkModel){
        viewModelScope.launch {
            _subscribeCallStatus.value = ApiCallStatus.LOADING
            try {
                val subResponse = Network.easyFlowServices.subscribeToPlan(
                    planSubscriptionModel = PlanSubscriptionModel(
                        ownerName = plan.ownerName,
                        planName = plan.name
                    )
                )
                if(subResponse.isSuccessful){
                    _subscribeCallStatus.value = ApiCallStatus.DONE
                }
            }
            catch (e: Exception){
                _subscribeCallStatus.value = ApiCallStatus.ERROR
            }
        }
    }

    fun onResponseRecieved() {
        _subscribeCallStatus.value = null
    }
}