package com.easyflow.appScreens.home.fragmentHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.easyflow.database.TicketDao
import com.easyflow.database.UserDao

class HomeFragmentViewModelFactory(private val ticketDao: TicketDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)){
            return HomeFragmentViewModel(ticketDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}