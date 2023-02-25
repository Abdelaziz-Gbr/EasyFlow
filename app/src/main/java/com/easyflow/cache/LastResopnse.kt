package com.easyflow.cache

import androidx.lifecycle.MutableLiveData

object LastResopnse {
    var code: Int? = null
    var msg : String? = null
    @Volatile
    var checkResponse : Boolean = false
//var checkResponse : MutableLiveData<Boolean> = MutableLiveData()
}