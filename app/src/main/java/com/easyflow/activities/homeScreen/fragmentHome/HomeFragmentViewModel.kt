package com.easyflow.activities.homeScreen.fragmentHome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.api.Network
import com.easyflow.cache.UserCache
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
        val auth = UserCache.userKey
        val userInfo = Network.easyFlowServices.getUserInfo(auth!!)
        if(userInfo.isSuccessful){
            UserCache.cacheUser(userInfo.body())
            UserCache.userKey = auth
            _welcomeText.value = "mr. ${UserCache.firstName}"
        }
    }

}