package com.easyflow.activities.signIn.signActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.database.TripDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignActivityViewModel(private val tripDao: TripDao): ViewModel() {
    val showOfflineOption = MutableLiveData<Boolean>()

    private var isThereTrips = false

    init {
        showOfflineOption.value = false
        viewModelScope.launch {
            val trips = withContext(Dispatchers.IO){
                tripDao.getAllTrips()
            }
            if(trips.isNotEmpty()) {
                showOfflineOption.value = true
                isThereTrips = true
            }
        }
    }

    fun onSignScreenoffFocus(){
        showOfflineOption.value = false
    }

    fun onSignScreenOnFocus(){
        showOfflineOption.value = isThereTrips
    }
}