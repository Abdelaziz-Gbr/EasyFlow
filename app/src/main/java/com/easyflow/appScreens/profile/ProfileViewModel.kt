package com.easyflow.appScreens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.cache.UserCache
import com.easyflow.network.Network
import com.easyflow.network.models.ProfileUpdateNetworkModel
import com.easyflow.network.models.ServerResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class ProfileViewModel : ViewModel() {

    var username: String? = UserCache.username
        set(value) {
            field = value
            UserCache.username = value
        }

    var firstName: String? = UserCache.firstName
        set(value) {
            field = value
            UserCache.firstName = value
        }

    var lastName: String? = UserCache.lastName
        set(value) {
            field = value
            UserCache.lastName = value
        }

    var email: String? = UserCache.email
        set(value) {
            field = value
            UserCache.email = value
        }

    var phoneNumber: String? = UserCache.phoneNumber
        set(value) {
            field = value
            UserCache.phoneNumber = value
        }

    var gender: String? = UserCache.gender
        set(value) {
            field = value
            UserCache.gender = value
        }

    private val _updateResponse = MutableLiveData<String?>()
    val updateResponse : LiveData<String?>
        get() = _updateResponse

    fun onGenderChanged(isChecked: Boolean, gender: String) {
        if (isChecked) {
            this.gender = gender
        }
    }


    fun getWalletBalance(): String {
        return "Current balance: ${UserCache.wallet?.balance ?: 0f}"
    }

    fun isMale(): Boolean = UserCache.gender == "M"
    fun isFemale(): Boolean = UserCache.gender == "F"
    fun updateProfile(profileUpdateNetworkModel: ProfileUpdateNetworkModel) {
        viewModelScope.launch{
            try {
                val response = Network.easyFlowServices.updateUserProfile(profileUpdateNetworkModel = profileUpdateNetworkModel)
                if(response.isSuccessful){
                    _updateResponse.value = response.body()!!.message
                }
                else{
                    _updateResponse.value = Gson().fromJson(response.errorBody()!!.charStream(), ServerResponse::class.java).message
                }
            }
            catch (e: Exception){
                _updateResponse.value = "error occured please try again later"
            }
        }

    }

}