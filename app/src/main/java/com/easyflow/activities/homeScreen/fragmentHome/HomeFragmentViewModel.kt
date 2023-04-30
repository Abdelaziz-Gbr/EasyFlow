package com.easyflow.activities.homeScreen.fragmentHome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.cache.UserCache
import com.easyflow.cache.UserKey
import com.easyflow.database.UserDao
import kotlinx.coroutines.launch

class HomeFragmentViewModel(private val userDao: UserDao): ViewModel() {
    private val _welcomeText = MutableLiveData<String>()
    val welcomeText : LiveData<String>
        get() = _welcomeText

    private val _enableNFCButton = MutableLiveData<Boolean>()
    val enableNFCButton : LiveData<Boolean>
        get() = _enableNFCButton



    init {
        _enableNFCButton.value = false
        viewModelScope.launch { getUserInfo() }

    }

    fun logOut(){
        viewModelScope.launch {
            userDao.removeUser()
            //todo delete all tickets as well. || just retrieve them from the internet each time.
        }

    }

    private suspend fun getUserInfo(){
        val auth = UserKey.value
        val userInfo = Network.easyFlowServices.getUserInfo(auth!!)
        try{
            if (userInfo.isSuccessful) {
                Log.d("user_wallet", userInfo.body()?.wallet?.balance.toString())
                UserCache.cacheUser(userInfo.body())
                _welcomeText.value = "mr. ${UserCache.firstName}"
            }
        }
        catch (e: Exception){
            //failed to retrieve user Info the program should break!.
        }
    }

}