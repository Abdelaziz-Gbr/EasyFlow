package com.easyflow.activities.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.easyflow.database.TripDao
import com.easyflow.database.UserDao

class SplashScreenViewModelFactory(private val dataSource : UserDao, private val tripsDao: TripDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SplashScreenViewModel::class.java)){
            return SplashScreenViewModel(dataSource, tripsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}