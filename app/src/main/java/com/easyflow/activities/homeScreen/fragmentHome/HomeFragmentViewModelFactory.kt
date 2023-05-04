package com.easyflow.activities.homeScreen.fragmentHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.easyflow.database.UserDao

class HomeFragmentViewModelFactory(private val userDao: UserDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)){
            return HomeFragmentViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}