package com.easyflow.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easyflow.network.models.TicketNetworkModel

@Entity(tableName = "Ticket_Table")
data class TicketDatabaseModel (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val startTime: String,
    val endTime: String,
    val status: String,
    val startStation: String,
    val price: Float,
    val endStation: String,
    val ownerName: String
        )

fun TicketDatabaseModel.toNetworkDomain(): TicketNetworkModel{
    return TicketNetworkModel(id, startTime, endTime, status, startStation, price, endStation, ownerName)
}