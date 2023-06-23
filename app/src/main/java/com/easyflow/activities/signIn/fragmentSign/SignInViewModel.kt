package com.easyflow.activities.signIn.fragmentSign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.network.Network
import com.easyflow.cache.UserCache
import com.easyflow.cache.UserKey
import com.easyflow.database.UserDao
import com.easyflow.database.models.UserDatabaseModel
import com.easyflow.network.models.UserNetworkModel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

class SignInViewModel(private val userDataSource: UserDao): ViewModel() {
    private val _signInResponse = MutableLiveData<Boolean?>()
    val signInResponse : LiveData<Boolean?>
        get() = _signInResponse
    //todo partly common with splash screen
    fun signIn(username: String, password: String){
        viewModelScope.launch {
            try {
                val logInResponse = Network.easyFlowServices.signIn(UserNetworkModel(username = username, password = password))
                if(!logInResponse.isSuccessful){
                    _signInResponse.value = false
                    return@launch
                }
                UserKey.value = logInResponse.headers()["Authorization"]
                FirebaseMessaging.getInstance().subscribeToTopic("$username")
                _signInResponse.value = true
            }
            catch (e: Exception){
                _signInResponse.value = false
            }
        }
    }

    //todo change responsibility to DB repository
    fun addUser(user: UserDatabaseModel){
        viewModelScope.launch { userDataSource.addUser(user) }
    }
    fun onResponseReceived(){
        _signInResponse.value = null
    }

}