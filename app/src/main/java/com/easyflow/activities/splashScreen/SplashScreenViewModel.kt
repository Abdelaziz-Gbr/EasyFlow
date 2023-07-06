package com.easyflow.activities.splashScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.cache.sharedPreferences
import com.easyflow.database.TicketDao
import com.easyflow.database.TripDao
import com.easyflow.database.UserDao
import com.easyflow.database.models.toNetworkModel
import com.easyflow.utils.signUserIn
import com.easyflow.utils.subscribeToMainFeed
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val userDao: UserDao, private val ticketDao: TicketDao, private val tripDao: TripDao): ViewModel() {
    private val _navigateTo = MutableLiveData<Int>()
    val navigateTo : LiveData<Int>
        get() = _navigateTo

    init {
        val subMain = sharedPreferences.data.getBoolean("sub_main", true)
        if(subMain){
            subscribeToMainFeed()
        }
        getUser()
    }

    private fun getUser(){
        viewModelScope.launch {
            val user = userDao.getUser()
            if(user == null){
                _navigateTo.value = 0
                return@launch
            }
            val signedIn = signUserIn(user.toNetworkModel(), userDao, ticketDao, true)
            if(signedIn != 2){
                if(signedIn == 0){
                    ticketDao.deleteAllTickets()
                    tripDao.deleteAllTrips()
                    userDao.removeUser()
                }
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

}