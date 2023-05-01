package com.easyflow.network.models

import com.easyflow.database.models.TicketDatabaseModel

data class TicketNetworkModel (
    val id: String,
    val price: Float,
    val time: Long,
    val weight: Int,
    val line_id: String,
    val owner_id: String,
)

fun TicketNetworkModel.toDatabaseDomain(): TicketDatabaseModel{
    return TicketDatabaseModel(id, price, time, weight, line_id, owner_id)
}