package com.easyflow.activities.splashScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.database.TicketDao
import com.easyflow.database.TripDao
import com.easyflow.database.UserDao
import com.easyflow.database.models.toNetworkModel
import com.easyflow.utils.signUserIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait

class SplashScreenViewModel(private val userDao: UserDao, private val ticketDao: TicketDao, private val tripDao: TripDao): ViewModel() {
    private val _navigateTo = MutableLiveData<Int>()
    val navigateTo : LiveData<Int>
        get() = _navigateTo

    fun getUser(){
        viewModelScope.launch {
            val user = userDao.getUser()
            if(user == null){
                _navigateTo.value = 0
                return@launch
            }
            val signedIn = signUserIn(user.toNetworkModel(), userDao, ticketDao)
            if(signedIn != 2){
                _navigateTo.value = signedIn
            }
            else{
                val trips = tripDao.getAllTrips()
                if(trips.isNotEmpty()){
                    _navigateTo.value = 2
                }
                else{
                    _navigateTo.value = 0
                }
            }
        }
    }
    fun onUserGot(){
        _navigateTo.value = 0
    }
}