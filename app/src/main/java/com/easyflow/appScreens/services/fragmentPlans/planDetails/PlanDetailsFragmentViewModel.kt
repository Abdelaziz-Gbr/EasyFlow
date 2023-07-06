package com.easyflow.appScreens.services.fragmentPlans.planDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.network.models.PlanNetworkModel
import com.easyflow.network.models.PlanSubscriptionModel
import kotlinx.coroutines.launch


class PlanDetailsFragmentViewModel : ViewModel() {
    private val _planSubscripe = MutableLiveData<String?>()
    val planSubscripe : LiveData<String?>
        get() = _planSubscripe
    fun subscripeToPlan(plan : PlanNetworkModel){
        viewModelScope.launch {
            try {
                val subResponse = Network.easyFlowServices.subscribeToPlan(
                    planSubscriptionModel = PlanSubscriptionModel(
                        ownerName = plan.ownerName,
                        planName = plan.name
                    )
                )
                _planSubscripe.value = subResponse.message()
            }
            catch (e: Exception){
                _planSubscripe.value = "ann error occured, please try again later."
            }
        }
    }

    fun onResponseRecieved() {
        _planSubscripe.value = null
    }
}