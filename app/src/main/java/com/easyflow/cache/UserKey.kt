package com.easyflow.cache

import android.database.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.properties.Delegates

object UserKey {

    @Volatile
    var key : String? = null

    //var key : MutableLiveData<String> = MutableLiveData()
}