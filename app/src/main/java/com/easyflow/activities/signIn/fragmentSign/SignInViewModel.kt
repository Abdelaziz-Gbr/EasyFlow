package com.easyflow.activities.signIn.fragmentSign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.database.TicketDao
import com.easyflow.database.UserDao
import com.easyflow.network.models.UserNetworkModel
import com.easyflow.utils.signUserIn
import kotlinx.coroutines.launch

class SignInViewModel(private val userDao: UserDao, private val ticketDao: TicketDao): ViewModel() {
    private val _signInResponse = MutableLiveData<Boolean?>()
    val signInResponse : LiveData<Boolean?>
        get() = _signInResponse

    fun signIn(username: String, password: String){
        viewModelScope.launch {
            _signInResponse.value = when(
                signUserIn(
                    UserNetworkModel(username = username, password = password)
                    , userDao
                    , ticketDao
                ))
            {
                1 ->  true
                else ->  false
            }
        }
    }

    fun onResponseReceived(){
        _signInResponse.value = null
    }

}