package com.easyflow.appScreens.home.fragmentHistory.ticketDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.easyflow.database.TicketDao

class TicketDetailViewModelFactory (private val TicketId: String, private val ticketDao: TicketDao) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TicketDetailViewModel::class.java)){
            return TicketDetailViewModel(TicketId, ticketDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}