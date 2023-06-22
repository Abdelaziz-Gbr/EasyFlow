package com.easyflow.activities.trips.fragmentTrips

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.database.TripDao
import kotlinx.coroutines.launch

class TripsFragmentViewModel(private val tripsDao: TripDao) : ViewModel() {
    private val _tripID = MutableLiveData<String>()
    val tripID : LiveData<String>
        get() = _tripID



    fun onFirstButtonClicked(){
        viewModelScope.launch {
            val trip = tripsDao.getFirstTrip()
            trip?.let{
                _tripID.value = trip.id
            }
        }
    }
    fun onSecondButtonClicked(){
        viewModelScope.launch {
            val trip = tripsDao.getSecondTrip()
            trip?.let{
                _tripID.value = trip.id
            }
        }

    }
    fun onThirdButtonClicked(){
        viewModelScope.launch {
            val trip = tripsDao.getThirdTrip()
            trip?.let{
                _tripID.value = trip.id
            }
        }

    }
    fun onFourthButtonClicked(){
        viewModelScope.launch {
            val trip = tripsDao.getFourthTrip()
            trip?.let{
                _tripID.value = trip.id
            }
        }

    }
    fun onFifthButtonClicked(){
        viewModelScope.launch {
            val trip = tripsDao.getFifthTrip()
            trip?.let{
                _tripID.value = trip.id
            }
        }

    }
}