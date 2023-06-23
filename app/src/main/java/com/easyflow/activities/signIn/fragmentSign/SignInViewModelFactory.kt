package com.easyflow.activities.signIn.fragmentSign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.easyflow.database.TicketDao
import com.easyflow.database.UserDao

class SignInViewModelFactory(private val userDao : UserDao, private val ticketDao: TicketDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SignInViewModel::class.java)){
            return SignInViewModel(userDao, ticketDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}