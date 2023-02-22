package com.easyflow.cache

import android.database.Observable
import kotlin.properties.Delegates

object UserKey {
    @Volatile
    var key : String? = null
}