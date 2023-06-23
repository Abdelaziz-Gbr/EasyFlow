package com.easyflow.cache

import android.content.Context
import android.content.SharedPreferences

object sharedPreferences {

    private lateinit var applicationContext: Context
    val data: SharedPreferences by lazy {
        applicationContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }

    fun init(context: Context) {
        applicationContext = context.applicationContext
    }
}