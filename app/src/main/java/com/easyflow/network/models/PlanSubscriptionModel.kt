package com.easyflow.network.models

import com.google.gson.annotations.SerializedName

data class PlanSubscriptionModel(
    @SerializedName("owner_name") var ownerName: String,
    @SerializedName("plan_name") var planName: String
)
