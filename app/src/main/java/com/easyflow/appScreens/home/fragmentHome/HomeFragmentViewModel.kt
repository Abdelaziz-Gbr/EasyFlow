package com.easyflow.appScreens.home.fragmentHome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.easyflow.network.Network
import com.easyflow.cache.UserCache
import com.easyflow.cache.UserKey
import com.easyflow.database.TicketDao
import com.easyflow.network.models.toDatabaseDomain
import kotlinx.coroutines.launch

class HomeFragmentViewModel(private val ticketDao: TicketDao): ViewModel() {
    private val _currentBalance = MutableLiveData<String>()
    val currentBalance : LiveData<String>
        get() = _currentBalance

    private val _navigateToTripsScreen = MutableLiveData<Boolean>()
    val navigateToTripsScreen : LiveData<Boolean>
        get () = _navigateToTripsScreen

    private val _navigateToRecharge = MutableLiveData<Boolean>()
    val navigateToRecharge : LiveData<Boolean>
        get() = _navigateToRecharge

    val tickets = ticketDao.getAllTickets()

init {
    updateBalance()
    viewModelScope.launch {
        val currentTickets = tickets.value
        if(currentTickets == null){
            refreshTickets(null)
        }
    }
}


    fun onNavigateToTripsActivityClicked(){
        _navigateToTripsScreen.value = true
    }

    fun onTripsNavigated(){
        _navigateToTripsScreen.value = false
    }

    fun onNavigateToRechargeClicked(){
        _navigateToRecharge.value = true
    }

    fun onRechargedNavigated(){
        _navigateToRecharge.value = false
    }
    fun updateBalance() {
        _currentBalance.value = "${UserCache.wallet?.balance}"
    }
    fun refreshTickets(swipeRefreshLayout: SwipeRefreshLayout?) {
        //get tickets from the internet
        viewModelScope.launch {
            val ticketsResponse = Network.easyFlowServices.getAllTickets(UserKey.value!!)
            if (ticketsResponse.isSuccessful) {
                val tickets = ticketsResponse.body()
                if(tickets != null){
                    ticketDao.insert(*tickets.map {
                        it.toDatabaseDomain()
                    }.toTypedArray())
                }
            }
            swipeRefreshLayout?.isRefreshing = false
        }
    }

}