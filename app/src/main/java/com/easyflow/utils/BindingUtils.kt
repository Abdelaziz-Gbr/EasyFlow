package com.easyflow.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.easyflow.database.models.TicketDatabaseModel

@BindingAdapter("ticketTime")
fun TextView.setTicketItemTime(ticket: TicketDatabaseModel?){
    if(ticket != null){
        text = convertLongToTime(ticket.startTime)
    }
}

@BindingAdapter("ticketPlace")
fun TextView.setTicketPlace(ticket: TicketDatabaseModel?){
    if(ticket != null){
        text = "from ${ticket.startStation} to ${ticket.endStation}"
    }
}

@BindingAdapter("ticketPrice")
fun TextView.setTicketPrice(ticket: TicketDatabaseModel?){
    if(ticket != null){
        text = "${ticket.price} EGP."
    }
}
@BindingAdapter("ticketId")
fun TextView.setTicketId(ticket: TicketDatabaseModel?){
    if(ticket != null){
        text = "Ticket Id: ${ticket.id}"
    }
}

@BindingAdapter("ticketStatus")
fun TextView.setTicketStatus(ticket: TicketDatabaseModel?){
    if(ticket != null){
        text = "Status: ${ticket.status}"
    }
}

@BindingAdapter("startStation")
fun TextView.fromPlace(ticket: TicketDatabaseModel?){
    if(ticket != null){
        text = "From: ${ticket.startStation}"
    }
}

@BindingAdapter("endStation")
fun TextView.endPlace(ticket: TicketDatabaseModel?){
    if(ticket != null){
        text = "To: ${ticket.endStation}"
    }
}