package com.easyflow.cache

import com.easyflow.database.models.UserDatabaseModel
import com.easyflow.network.models.UserNetworkModel

object UserCache {
    var firstName: String? = null
    var lastName: String? = null
    var username: String? = null
    var email: String? = null
    var password: String? = null
    var phoneNumber: String? = null
    var gender: String? = null
    var birthDay: String? = null
    var type : String? = null
    var city : String? = null
    var balance : Float? = null
    fun cacheUser(user: UserNetworkModel?){
        if(user == null)
            return
        firstName = user.firstName
        lastName = user.lastName
        username = user.username
        email = user.email
        password = user.password
        phoneNumber = user.phoneNumber
        gender = user.gender
        birthDay = user.birthDay
        type = user.type
        city = user.city
        balance = user.wallet?.balance
    }

    fun freeAll(){
        firstName = null
        lastName = null
        username = null
        email = null
        password = null
        phoneNumber = null
        gender = null
        birthDay = null
        type = null
        city = null
        balance = null
    }
}