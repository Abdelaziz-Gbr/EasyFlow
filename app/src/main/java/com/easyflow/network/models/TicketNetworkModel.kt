package com.easyflow.network.models

import com.easyflow.database.models.TicketDatabaseModel

data class TicketNetworkModel (
    val id: String,
    val startTime: String,
    val endTime: String,
    val status: String,
    val startStation: String,
    val price: Float,
    val endStation: String)

fun TicketNetworkModel.toDatabaseDomain(): TicketDatabaseModel{
    return TicketDatabaseModel(id, startTime, endTime, status, startStation, price, endStation)
}