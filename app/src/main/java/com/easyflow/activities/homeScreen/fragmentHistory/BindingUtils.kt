package com.easyflow.activities.homeScreen.fragmentHistory

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.easyflow.database.models.TicketDatabaseModel
import com.easyflow.utils.convertLongToTime

@BindingAdapter("ticketTime")
fun TextView.setTicketItemTime(ticket: TicketDatabaseModel){
    ticket.let {
        text = convertLongToTime(it.startTime)
    }
}

@BindingAdapter("ticketPlace")
fun TextView.setTicketPlace(ticket: TicketDatabaseModel){
    ticket.let {
        text = "from ${it.startStation} to ${ticket.endStation}"
    }
}

@BindingAdapter("ticketPrice")
fun TextView.setTicketPrice(ticket: TicketDatabaseModel){
    ticket.let {
        text = "${it.price} EGP."
    }
}