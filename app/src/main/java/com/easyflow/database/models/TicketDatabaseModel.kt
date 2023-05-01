package com.easyflow.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easyflow.network.models.TicketNetworkModel

@Entity(tableName = "Ticket_Table")
data class TicketDatabaseModel (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val price: Float,
    val time: Long,
    val weight: Int,
    val line_id: String,
    val owner_id: String,
        )

fun TicketDatabaseModel.toNetworkDomain(): TicketNetworkModel{
    return TicketNetworkModel(id, price, time, weight, line_id, owner_id)
}