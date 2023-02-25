package com.easyflow.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.cache.UserKey
import com.easyflow.models.ServerResponse
import com.easyflow.models.User
import com.easyflow.repository.NetworkRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class NetworkViewModel(private val networkRepository: NetworkRepository): ViewModel() {
    val registerResponse : MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val signinResponse : MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val userInfoResponse : MutableLiveData<Response<User>> = MutableLiveData()

    fun signIn(user : User){
        viewModelScope.launch {
            val response = networkRepository.signIn(user)
            signinResponse.value = response
        }
    }

    fun getUserInfo(){
        viewModelScope.launch{
            val response = networkRepository.getUserInfo(UserKey.key!!)
            userInfoResponse.value = response
        }
    }

    fun register(user:User){
        viewModelScope.launch {
            val response = networkRepository.register(user)
            registerResponse.value = response
        }
    }
}