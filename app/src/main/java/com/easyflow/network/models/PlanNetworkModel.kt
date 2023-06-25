package com.easyflow.network.models

data class PlanNetworkModel(
    val name: String,
    val ownerName: String,
    val price: Float,
    val privilege : String,
    val discountRate: Float,
    val durationDays: Int,
    val maxCompanion: String,
    val numberOfTrips: Int,
    val available: Boolean
)
