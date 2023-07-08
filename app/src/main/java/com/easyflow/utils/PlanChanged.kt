package com.easyflow.utils

import androidx.lifecycle.MutableLiveData
import com.easyflow.network.models.UserPlan

object PlanChanged {
    val plan = MutableLiveData<UserPlan>()
}