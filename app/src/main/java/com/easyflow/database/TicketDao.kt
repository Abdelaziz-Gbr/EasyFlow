package com.easyflow.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.easyflow.database.models.TicketDatabaseModel

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg ticket: TicketDatabaseModel)

    @Update
    suspend fun update(ticket: TicketDatabaseModel)

    @Query("SELECT * FROM Ticket_Table ORDER BY time")
    fun getAllTickets(): LiveData<List<TicketDatabaseModel>>

    @Query("SELECT * FROM Ticket_Table WHERE id = :ticketID")
    fun getTicketWithId(ticketID: String): LiveData<TicketDatabaseModel>
}