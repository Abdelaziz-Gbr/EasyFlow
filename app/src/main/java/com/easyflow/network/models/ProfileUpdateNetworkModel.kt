package com.easyflow.network.models

data class ProfileUpdateNetworkModel(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val gender: String
    )
