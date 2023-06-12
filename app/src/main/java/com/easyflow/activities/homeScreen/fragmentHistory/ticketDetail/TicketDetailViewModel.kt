package com.easyflow.activities.homeScreen.fragmentHistory.ticketDetail

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.easyflow.database.TicketDao
import com.easyflow.database.models.TicketDatabaseModel

class TicketDetailViewModel(private val ticketId: String, private val dataSource : TicketDao) : ViewModel() {

    val database = dataSource

    private val ticket = MediatorLiveData<TicketDatabaseModel>()

    fun getTicket(): MediatorLiveData<TicketDatabaseModel> {
        Log.d("tick1", ticketId)
        Log.d("tick12", "${ticket.value?.id}")
        return ticket
    }

    init {
        ticket.addSource(database.getTicketWithId(ticketId), ticket::setValue)
    }
}