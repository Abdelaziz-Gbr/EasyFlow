package com.easyflow.network.models

data class UserNetworkModel (
    var type: String? = null,
    var active: Boolean? = null,
    var username: String? = null,
    var phoneNumber: String? = null,
    var firstName: String? = null,
    var email: String? = null,
    var birthDay: String? = null,
    var city: String? = null,
    var lastName: String? = null,
    var gender: String? = null,
    var wallet: Wallet? = null,
    var password: String? = null
        )
