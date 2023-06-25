package com.easyflow.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easyflow.appScreens.services.fragmentPlans.PlanListAdapter
import com.easyflow.database.models.TicketDatabaseModel
import com.easyflow.network.models.PlanNetworkModel

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

@BindingAdapter("discountRate")
fun TextView.setDiscount(disRate: Float?){
    disRate?.let {
        text = String.format("discount rate: %.1f", disRate*100)
    }
}

@BindingAdapter("isPlanAvailable")
fun TextView.isPlanAvailable(isAvailable: Boolean?){
    isAvailable?.let {
        text = if(isAvailable) "You can request a subscribe to this plan." else "this plan is currently unavailable."
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data : List<PlanNetworkModel>?){
    val adapter = recyclerView.adapter as PlanListAdapter
    adapter.submitList(data)
}