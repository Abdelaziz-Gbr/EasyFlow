package com.easyflow.activities.homeScreen.fragmentHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.easyflow.database.TicketDao

class HistoryFragmentViewModelFactory (private val ticketDao: TicketDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HistoryFragmentViewModel::class.java)){
            return HistoryFragmentViewModel(ticketDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}