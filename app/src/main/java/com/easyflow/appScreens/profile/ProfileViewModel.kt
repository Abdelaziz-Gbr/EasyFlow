package com.easyflow.appScreens.profile

import androidx.lifecycle.ViewModel
import com.easyflow.cache.UserCache


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

    fun onGenderChanged(isChecked: Boolean, gender: String) {
        if (isChecked) {
            this.gender = gender
        }
    }

    fun saveUser() {
        //todo make api call to server to save the changes.
    }


    fun getWalletBalance(): String {
        return "Current balance: ${UserCache.wallet?.balance ?: 0f}"
    }

    fun isMale(): Boolean = UserCache.gender == "M"
    fun isFemale(): Boolean = UserCache.gender == "F"

}