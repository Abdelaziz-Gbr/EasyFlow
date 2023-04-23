package com.easyflow.activities.splashScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.api.EasyFlowServices
import com.easyflow.cache.UserCache
import com.easyflow.database.UserDao
import com.easyflow.models.User
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val dataSource: UserDao): ViewModel() {
    private val _navigateToHomeScreen = MutableLiveData<Boolean?>()
    val navigateToHomeScreen : LiveData<Boolean?>
        get() = _navigateToHomeScreen

    fun getUser(){
        //todo common function between login & splash screen -> move to repository.
        viewModelScope.launch {
            //get User from DB
            val tempUser = dataSource.getUser()
            if(tempUser == null){
                _navigateToHomeScreen.value = false
                return@launch
            }
            //test user credentials
            //todo change it when User DB model separates from Network Model
            val logInResponse = EasyFlowServices.api.signIn(User(id = null, username = tempUser.username, password = tempUser.password))
            if(!logInResponse.isSuccessful){
                _navigateToHomeScreen.value = false
                return@launch
            }
            tempUser.userKey = logInResponse.headers()["Authorization"]
            UserCache.cacheUser(tempUser)
            _navigateToHomeScreen.value = true
        }
    }
    fun onUserGot(){
        _navigateToHomeScreen.value = null
    }
}