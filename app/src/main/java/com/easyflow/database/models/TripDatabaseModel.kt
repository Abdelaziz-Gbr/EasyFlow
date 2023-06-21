package com.easyflow.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripDatabaseModel(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val used: Boolean = false
)