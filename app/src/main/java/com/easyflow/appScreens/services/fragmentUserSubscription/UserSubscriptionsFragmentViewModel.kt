package com.easyflow.appScreens.services.fragmentUserSubscription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.network.models.UserPlan
import kotlinx.coroutines.launch

class UserSubscriptionsFragmentViewModel: ViewModel() {
    private val _subscriptions = MutableLiveData<List<UserPlan>>()
    val subscriptions : LiveData<List<UserPlan>>
        get() = _subscriptions

    private val _noSubs = MutableLiveData<Boolean>(true)
    val noSubs : LiveData<Boolean>
        get() = _noSubs

    init {
        viewModelScope.launch { getSubs() }
    }

    private suspend fun getSubs(){
        try{
            val subsResponse = Network.easyFlowServices.getUserSubscriptions()
            if (subsResponse.isSuccessful) {
                if(subsResponse.body()!!.isNotEmpty()) {
                    _noSubs.value = false
                    _subscriptions.value = subsResponse.body()
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
}