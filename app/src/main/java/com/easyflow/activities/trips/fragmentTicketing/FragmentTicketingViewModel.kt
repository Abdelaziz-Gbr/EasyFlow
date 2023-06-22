package com.easyflow.activities.trips.fragmentTicketing

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.cache.UserKey
import com.easyflow.database.TripDao
import com.easyflow.network.Network
import com.easyflow.network.models.toDatabaseModel
import kotlinx.coroutines.launch

class FragmentTicketingViewModel(private val tripsDao: TripDao) : ViewModel() {

    init {
        viewModelScope.launch {
            try {
                updateTrips()
            }
            catch (e: Exception){
                //do nothing i.e don't update the database.
            }
        }
    }

    private suspend fun updateTrips() {
        if(UserKey.value != null){
            val response = Network.easyFlowServices.getTrips(UserKey.value!!)
            if(response.isSuccessful){
                tripsDao.deleteAllTrips()
                Log.d("trips", "sucess")
                val trips = response.body()
                if(trips != null){
                    Log.d("trips", "inserting ${trips.size} trips")
                    tripsDao.insert(*trips.map {
                        it.toDatabaseModel()
                    }.toTypedArray()
                    )
                }
            }

        }
    }

}