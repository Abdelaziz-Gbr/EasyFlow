package com.easyflow.activities.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.api.EasyFlowServices
import com.easyflow.cache.UserCache
import com.easyflow.database.UserDao
import com.easyflow.models.User
import kotlinx.coroutines.launch

class SignInViewModel(private val userDataSource: UserDao): ViewModel() {
    private val _signInResponse = MutableLiveData<Boolean?>()
    val signInResponse : LiveData<Boolean?>
        get() = _signInResponse
    //todo partly common with splash screen
    fun signIn(username: String, password: String){
        viewModelScope.launch {
            //todo change when user model splits from network & db
            try {
                //todo delegate initiating temp user to api class.
                val logInResponse = EasyFlowServices.api.signIn(User(id = null, username = username, password = password))
                if(!logInResponse.isSuccessful){
                    _signInResponse.value = false
                    return@launch
                }
                val tempUser = User(username = username, password = password)
                tempUser.userKey = logInResponse.headers()["Authorization"]
                UserCache.cacheUser(tempUser)
                _signInResponse.value = true
            }
            catch (e: Exception){
                _signInResponse.value = false
            }
        }
    }

    //todo change responsibility to DB repository
    fun addUser(user: User){
        viewModelScope.launch { userDataSource.addUser(user) }
    }
    fun onResponseReceived(){
        _signInResponse.value = null
    }
}