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

    @Query("SELECT * FROM Ticket_Table ORDER BY startTime DESC")
    fun getAllTickets(): LiveData<List<TicketDatabaseModel>>

    @Query("SELECT * from Ticket_Table WHERE id = :ticketID")
    fun getTicketWithId(ticketID: String): LiveData<TicketDatabaseModel>

    @Query("DELETE FROM Ticket_Table")
    suspend fun deleteAllTickets()
}