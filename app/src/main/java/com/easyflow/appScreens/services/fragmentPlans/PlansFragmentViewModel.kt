package com.easyflow.appScreens.services.fragmentPlans

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.network.models.PlanNetworkModel
import kotlinx.coroutines.launch

enum class EasyFlowApiStatus{ LOADING, ERROR, DONE}
class PlansFragmentViewModel: ViewModel() {
    private val _status = MutableLiveData<EasyFlowApiStatus>()
    val status: LiveData<EasyFlowApiStatus>
        get() = _status

    private val _plans = MutableLiveData<List<PlanNetworkModel>>()
    val plans:LiveData<List<PlanNetworkModel>>
        get() = _plans


    private val _navigateToPlanDetail = MutableLiveData<PlanNetworkModel?>()
    val navigateToPlanDetail : LiveData<PlanNetworkModel?>
        get() = _navigateToPlanDetail


    init {
        _status.value = EasyFlowApiStatus.LOADING
        viewModelScope.launch {
            try{
                val plansReq = Network.easyFlowServices.getAllPlans()
                _status.value = EasyFlowApiStatus.DONE
                _plans.value = plansReq
            }
            catch (e: Exception){
                _status.value = EasyFlowApiStatus.ERROR
                _plans.value = ArrayList()
            }
        }
    }

    fun displayPlanDetails(plan: PlanNetworkModel){
        _navigateToPlanDetail.value = plan
    }

    fun onPlanDetailsNavigated(){
        _navigateToPlanDetail.value = null
    }
}