package com.easyflow.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
data class User (
        @PrimaryKey(autoGenerate = false) var id: Int? = 0,
        var firstName: String? = null,
        var lastName: String? = null,
        var username: String? = null,
        var email: String? = null,
        var password: String? = null,
        var phoneNumber: String? = null,
        var gender: String? = null,
        var birthDay: String? = null,
        var userKey: String? = null
        )