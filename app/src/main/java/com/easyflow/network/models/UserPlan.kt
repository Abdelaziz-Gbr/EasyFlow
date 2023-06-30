package com.easyflow.network.models

data class UserPlan (
    val repurchase: Boolean,
    val remainingTrips: Int,
    val planOwnerName: String,
    val planName: String,
    val expireDate: String
        )