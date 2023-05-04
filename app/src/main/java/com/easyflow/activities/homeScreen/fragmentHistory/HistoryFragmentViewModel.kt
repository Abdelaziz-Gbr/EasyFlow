package com.easyflow.activities.homeScreen.fragmentHistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.cache.UserKey
import com.easyflow.database.TicketDao
import com.easyflow.network.Network
import com.easyflow.network.models.TicketNetworkModel
import com.easyflow.network.models.toDatabaseDomain
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HistoryFragmentViewModel(private val ticketDao: TicketDao): ViewModel() {
    val tickets = ticketDao.getAllTickets()
    init {
        //refreshTickets()
        viewModelScope.launch { ticketDao.deleteAllTickets() }
    }

    private fun refreshTickets() {
        //get tickets from the internet
        viewModelScope.launch {
            try{
                val ticketsResponse = Network.easyFlowServices.getAllTickets(UserKey.value!!)
                if (ticketsResponse.isSuccessful) {
                    val tickets = ticketsResponse.body()
                    if(tickets != null){
                        ticketDao.insert(*tickets.map {
                            it.toDatabaseDomain()
                        }.toTypedArray())
                    }
                }
                else{
                    //todo show an error screen
                }
            }
            catch (e: HttpException){

                //todo show a retry again screen
            }
        }
    }

    fun onRefreshButtonClicked(){
        refreshTickets()
    }
}