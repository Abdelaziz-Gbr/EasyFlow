package com.easyflow.activities.splashScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.cache.UserKey
import com.easyflow.database.TripDao
import com.easyflow.database.UserDao
import com.easyflow.database.models.toNetworkModel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketTimeoutException

class SplashScreenViewModel(private val dataSource: UserDao, private val tripsDao: TripDao): ViewModel() {
    private val _navigateTo = MutableLiveData<Int>()
    val navigateTo : LiveData<Int>
        get() = _navigateTo

    fun getUser(){
        //todo common function between login & splash screen -> move to repository.
        viewModelScope.launch {
            //get UserDatabaseModel from DB
            val tempUser = dataSource.getUser()
            if(tempUser == null){
                _navigateTo.value = 1// go to sign in Page
                return@launch
            }
            //test user credentials
            try {
                //UserNetworkModel(username = tempUser.username, password = tempUser.password)
                val logInResponse = Network.easyFlowServices.signIn(tempUser.toNetworkModel())
                if(!logInResponse.isSuccessful){
                    _navigateTo.value = 1// go to sign in page -- invalid credtintials
                    return@launch
                }
                UserKey.value = logInResponse.headers()["Authorization"]
                FirebaseMessaging.getInstance().subscribeToTopic("${tempUser.username}")
                _navigateTo.value = 2// go to home screen
            }
            catch (e: Exception){
                val offlineModeExceptions : List<Class<out Exception>> = listOf(ConnectException::class.java, SocketTimeoutException::class.java)
                if(offlineModeExceptions.contains(e.javaClass)){
                    val trips = withContext(Dispatchers.IO){
                        tripsDao.getAllTrips()
                    }
                    if(trips.isNotEmpty())
                        _navigateTo.value = 3// go to offline mode
                    else
                        _navigateTo.value = 1
                }
                else{
                    _navigateTo.value = 1
                }

            }
        }
    }
    fun onUserGot(){
        _navigateTo.value = 0
    }
}