package com.easyflow.appScreens.services.fragmentUserSubscription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.network.models.UserPlan
import kotlinx.coroutines.launch

class UserSubscriptionsFragmentViewModel: ViewModel() {
    private val _subscriptions = MutableLiveData<MutableList<UserPlan>>()
    val subscriptions : LiveData<MutableList<UserPlan>>
        get() = _subscriptions

    private val _noSubs = MutableLiveData<Boolean>(true)
    val noSubs : LiveData<Boolean>
        get() = _noSubs

    private val _managePlan = MutableLiveData<UserPlan?>()
    val managePlan : LiveData<UserPlan?>
        get() = _managePlan

    var lastPosition = -1
    init {
        viewModelScope.launch { getSubs() }
    }

    private suspend fun getSubs(){
        try{
            val subsResponse = Network.easyFlowServices.getUserSubscriptions()
            if (subsResponse.isSuccessful) {
                if(subsResponse.body()!!.isNotEmpty()) {
                    _noSubs.value = false
                    _subscriptions.value = subsResponse.body() as MutableList<UserPlan>?
                }
            }
            else{
                _subscriptions.value = ArrayList()
            }
        }
        catch (e: Exception){
            _subscriptions.value = ArrayList()
        }
    }

    fun navigateToManagePlanClicked(position: Int) {
        lastPosition = position
        _managePlan.value = _subscriptions.value!![position]
    }

    fun onManagePlanNavigated(){
        _managePlan.value = null
    }

    fun addPlan(plan: UserPlan) {

        _subscriptions.value?.set(lastPosition, plan)
    }
}