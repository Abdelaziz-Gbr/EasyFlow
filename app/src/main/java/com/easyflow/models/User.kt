package com.easyflow.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
data class User (
        @PrimaryKey(autoGenerate = false)
        val id: Int? = 0,
        @SerializedName("username")
        var userName: String? = null,
        @SerializedName("password")
        var password: String? = null,
        var userKey: String? = null
        )