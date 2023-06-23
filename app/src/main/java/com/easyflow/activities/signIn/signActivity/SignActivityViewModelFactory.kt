package com.easyflow.activities.signIn.signActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.easyflow.database.TripDao


class SignActivityViewModelFactory(private val dataSource : TripDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SignActivityViewModel::class.java)){
            return SignActivityViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}