package com.easyflow.appScreens.services.fragmentPlans

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.network.models.PlanNetworkModel
import kotlinx.coroutines.launch

class PlansFragmentViewModel: ViewModel() {
    private val _statusError = MutableLiveData<String>()
    val statusError : LiveData<String>
        get() = _statusError


    private val _plans = MutableLiveData<List<PlanNetworkModel>>()
    val plans:LiveData<List<PlanNetworkModel>>
        get() = _plans


    init {
        viewModelScope.launch {
            try{
                val plansReq = Network.easyFlowServices.getAllPlans()
                _plans.value = plansReq
            }
            catch (e: Exception){
                _statusError.value = e.message
            }
        }
    }
}