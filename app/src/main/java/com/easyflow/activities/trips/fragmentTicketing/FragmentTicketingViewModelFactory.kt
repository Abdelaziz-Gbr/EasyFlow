package com.easyflow.activities.trips.fragmentTicketing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.easyflow.database.TripDao


class FragmentTicketingViewModelFactory(private val tripDao: TripDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FragmentTicketingViewModel::class.java)){
            return FragmentTicketingViewModel(tripDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}