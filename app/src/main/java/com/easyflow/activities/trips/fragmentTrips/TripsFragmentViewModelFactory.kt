package com.easyflow.activities.trips.fragmentTrips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.easyflow.database.TripDao

class TripsFragmentViewModelFactory(private val tripDao: TripDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TripsFragmentViewModel::class.java)){
            return TripsFragmentViewModel(tripDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}