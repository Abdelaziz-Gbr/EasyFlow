package com.easyflow.activities.splashScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.cache.UserCache
import com.easyflow.cache.UserKey
import com.easyflow.database.UserDao
import com.easyflow.database.models.UserDatabaseModel
import com.easyflow.database.models.toNetworkModel
import com.easyflow.network.models.UserNetworkModel
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val dataSource: UserDao): ViewModel() {
    private val _navigateToHomeScreen = MutableLiveData<Boolean?>()
    val navigateToHomeScreen : LiveData<Boolean?>
        get() = _navigateToHomeScreen

    fun getUser(){
        //todo common function between login & splash screen -> move to repository.
        viewModelScope.launch {
            //get UserDatabaseModel from DB
            val tempUser = dataSource.getUser()
            if(tempUser == null){
                _navigateToHomeScreen.value = false
                return@launch
            }
            //test user credentials
            try {
                //UserNetworkModel(username = tempUser.username, password = tempUser.password)
                val logInResponse = Network.easyFlowServices.signIn(tempUser.toNetworkModel())
                if(!logInResponse.isSuccessful){
                    _navigateToHomeScreen.value = false
                    return@launch
                }
                UserKey.value = logInResponse.headers()["Authorization"]
                _navigateToHomeScreen.value = true
            }
            catch (e: Exception){
                _navigateToHomeScreen.value = false
            }
        }
    }
    fun onUserGot(){
        _navigateToHomeScreen.value = null
    }
}