package com.easyflow.utils

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easyflow.R
import com.easyflow.appScreens.services.fragmentPlans.EasyFlowApiStatus
import com.easyflow.appScreens.services.fragmentPlans.PlanListAdapter
import com.easyflow.appScreens.services.fragmentUserSubscription.SubscriptionsListAdapter
import com.easyflow.database.models.TicketDatabaseModel
import com.easyflow.network.models.PlanNetworkModel
import com.easyflow.network.models.UserPlan
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("ticketTime")
fun TextView.setTicketItemTime(ticket: TicketDatabaseModel?){
    ticket?.let {
        val dateTime = LocalDateTime.parse(ticket.startTime, DateTimeFormatter.ISO_DATE_TIME)
        val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy\nh:mm a")
        text = dateTime.format(formatter)
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
        text = "Total Price: ${ticket.price} EGP."
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
    ticket?.let {
        text = "status: ${ticket.status}"
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
        text = String.format("discount rate: %.1f%%", disRate*100)
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

@BindingAdapter("subData")
fun bindSubData(recyclerView: RecyclerView, data : List<UserPlan>?){
    val adapter = recyclerView.adapter as SubscriptionsListAdapter
    adapter.submitList(data)
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("exprDate")
fun TextView.setExprDate(exprDate: String?){
    exprDate?.let {
        val dateTime = LocalDateTime.parse(exprDate, DateTimeFormatter.ISO_DATE_TIME)
        val formatter = DateTimeFormatter.ofPattern("MMM d @ h:mm a")
        text =  "Expire Date: ${dateTime.format(formatter)}"
    }
}

@BindingAdapter("noSubs")
fun TextView.setVis(visiable: Boolean){
    visibility =
        if (visiable) View.VISIBLE
        else View.GONE
}

@BindingAdapter("easyFlowApiStatus")
fun bindStatus(statusImageView: ImageView, status: EasyFlowApiStatus?){
    when(status){
        EasyFlowApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        EasyFlowApiStatus.ERROR ->{
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        else ->{
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("setOwnerLogo")
fun bindOwnerLogo(ownerLogo : ImageView, ownerName: TicketDatabaseModel){
    when(ownerName){
        else ->{
            ownerLogo.setImageResource(R.drawable.ic_plan)
        }
    }
}

@BindingAdapter("Repurchased")
fun Switch.isPlanRepurchased(repurchased : Boolean){
    isChecked = repurchased
}