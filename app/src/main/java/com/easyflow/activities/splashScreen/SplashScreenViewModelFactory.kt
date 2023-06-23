package com.easyflow.activities.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.easyflow.database.TicketDao
import com.easyflow.database.TripDao
import com.easyflow.database.UserDao

class SplashScreenViewModelFactory(private val userDao : UserDao, private val ticketDao: TicketDao, private val tripDao: TripDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SplashScreenViewModel::class.java)){
            return SplashScreenViewModel(userDao, ticketDao, tripDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}