package com.easyflow.activities.homeScreen.fragmentHome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.cache.UserCache
import com.easyflow.cache.UserKey
import com.easyflow.database.UserDao
import kotlinx.coroutines.launch

class HomeFragmentViewModel(private val userDao: UserDao): ViewModel() {
    private val _welcomeText = MutableLiveData<String>()
    val welcomeText : LiveData<String>
        get() = _welcomeText

    private val _currentBalance = MutableLiveData<String>()
    val currentBalance : LiveData<String>
        get() = _currentBalance

    private val _navigateToTripsScreen = MutableLiveData<Boolean>()
    val navigateToTripsScreen : LiveData<Boolean>
        get () = _navigateToTripsScreen

    private val _navigateToRechargeScreen = MutableLiveData<Boolean>()
    val navigateToRechargeScreen : LiveData<Boolean>
        get () = _navigateToRechargeScreen

    private val _navigateToHistoryFragment = MutableLiveData<Boolean>()
    val navigateToHistoryFragment : LiveData<Boolean>
        get () = _navigateToHistoryFragment


    init {
        viewModelScope.launch { getUserInfo() }

    }


    private suspend fun getUserInfo(){
        val auth = UserKey.value
        val userInfo = Network.easyFlowServices.getUserInfo(auth!!)
        try{
            if (userInfo.isSuccessful) {
                Log.d("user_wallet", userInfo.body()?.wallet?.balance.toString())
                UserCache.cacheUser(userInfo.body())
                _welcomeText.value = "mr. ${UserCache.firstName}"
                val currentAmountString = (UserCache.wallet!!.balance).toString()
                _currentBalance.value = "current balance = $currentAmountString"
            }
        }
        catch (e: Exception){
            throw Exception("Failed to Retrieve User Info.")
        }
    }

    fun onNavigateToTripsActivityClicked(){
        _navigateToTripsScreen.value = true
    }

    fun onTripsNavigated(){
        _navigateToTripsScreen.value = false
    }
    fun onNavigteToRechargeClicked(){
        _navigateToRechargeScreen.value = true
    }

    fun onRechargeNavigated(){
        _navigateToRechargeScreen.value = false
    }

    fun onHistoryClicked(){
        _navigateToHistoryFragment.value = true
    }

    fun onHistoryNavigated(){
        _navigateToHistoryFragment.value = false
    }

}