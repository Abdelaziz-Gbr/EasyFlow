package com.easyflow.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.cache.UserCache
import com.easyflow.database.models.UserDatabaseModel
import com.easyflow.repository.NetworkRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class NetworkViewModel(private val networkRepository: NetworkRepository): ViewModel() {/*
    val registerResponse : MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    private val _signResponse : MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val userInfoResponse : MutableLiveData<Response<UserDatabaseModel>> = MutableLiveData()
    val loginResponse : MutableLiveData<Boolean> = MutableLiveData()
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.d("Network", "Caught $exception")
        loginResponse.value = false
    }
    fun signIn(user : UserDatabaseModel){
        viewModelScope.launch (handler) {
            val response = networkRepository.signIn(user)
            //signResponse.value = response
            if(!response.isSuccessful){
                loginResponse.value = false
            }
            else{
                val header = response.headers()["Authorization"]
                if (header != null) {
                    getUserInfo(header)
                }
                else{
                    loginResponse.value = false
                }
            }
        }
    }

    private fun getUserInfo(auth: String){
        viewModelScope.launch(handler) {
            val response = networkRepository.getUserInfo(auth)
            //userInfoResponse.value = response
            if(!response.isSuccessful){
                loginResponse.value = false
            }
            else{
                val user = response.body()
                UserCache.cacheUser(user)
                loginResponse.value = true
            }
        }
    }

    fun register(user: UserDatabaseModel){
        viewModelScope.launch(handler) {
            val response = networkRepository.register(user)
            registerResponse.value = response
        }
    }*/
}