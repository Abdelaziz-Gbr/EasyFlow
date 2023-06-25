package com.easyflow.appScreens.home.fragmentHome

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

    private val _navigateToHistoryFragment = MutableLiveData<Boolean>()
    val navigateToHistoryFragment : LiveData<Boolean>
        get () = _navigateToHistoryFragment




    fun onNavigateToTripsActivityClicked(){
        _navigateToTripsScreen.value = true
    }

    fun onTripsNavigated(){
        _navigateToTripsScreen.value = false
    }

    fun onHistoryClicked(){
        _navigateToHistoryFragment.value = true
    }

    fun onHistoryNavigated(){
        _navigateToHistoryFragment.value = false
    }

}