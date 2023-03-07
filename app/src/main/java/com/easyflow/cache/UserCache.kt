package com.easyflow.cache

import com.easyflow.models.User

object UserCache {
    var firstName: String? = null
    var lastName: String? = null
    var username: String? = null
    var email: String? = null
    var password: String? = null
    var phoneNumber: String? = null
    var gender: String? = null
    var birthDay: String? = null
    var userKey: String? = null
    var type : String? = null
    var city : String? = null
    var balance : Double? = null
    fun cacheUser(user: User?){
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
        userKey = user.userKey
        type = user.type
        city = user.city
        balance = user.balance
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
        userKey = null
        type = null
        city = null
        balance = null
    }
}