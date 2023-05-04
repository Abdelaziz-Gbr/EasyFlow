package com.easyflow.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easyflow.network.models.UserNetworkModel

@Entity(tableName = "user_table")
data class UserDatabaseModel (
        @PrimaryKey(autoGenerate = false) var id: Int? = 0,
        var username: String? = null,
        var email: String? = null,
        var password: String? = null,
        )

fun UserDatabaseModel.toNetworkModel():UserNetworkModel{
        return UserNetworkModel(username = username, password = password, email = email)
}