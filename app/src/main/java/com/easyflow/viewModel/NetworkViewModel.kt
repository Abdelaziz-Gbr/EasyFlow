package com.easyflow.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.models.User
import com.easyflow.repository.NetworkRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class NetworkViewModel(private val networkRepository: NetworkRepository): ViewModel() {
    val registerResponse : MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val signResponse : MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val userInfoResponse : MutableLiveData<Response<User>> = MutableLiveData()
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.d("Network", "Caught $exception")
        //todo handle splash screen freezing on socket time out.
    }
    fun signIn(user : User){
        viewModelScope.launch (handler) {
            val response = networkRepository.signIn(user)
            signResponse.value = response
        }
    }

    fun getUserInfo(auth: String){
        viewModelScope.launch(handler) {
            val response = networkRepository.getUserInfo(auth)
            userInfoResponse.value = response
        }
    }

    fun register(user:User){
        viewModelScope.launch(handler) {
            val response = networkRepository.register(user)
            registerResponse.value = response
        }
    }
}