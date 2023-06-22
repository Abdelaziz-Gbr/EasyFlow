package com.easyflow.network.models

import com.easyflow.database.models.TripDatabaseModel

data class TripNetworkModel(val id: String)

fun TripNetworkModel.toDatabaseModel() : TripDatabaseModel{
    return TripDatabaseModel(this.id, false)
}
