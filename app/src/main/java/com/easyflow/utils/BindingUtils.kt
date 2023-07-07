package com.easyflow.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easyflow.R
import com.easyflow.appScreens.services.fragmentPlans.EasyFlowApiStatus
import com.easyflow.appScreens.services.fragmentPlans.PlanListAdapter
import com.easyflow.appScreens.services.fragmentUserSubscription.SubscriptionsListAdapter
import com.easyflow.database.models.TicketDatabaseModel
import com.easyflow.network.Network
import com.easyflow.network.models.PlanNetworkModel
import com.easyflow.network.models.UserPlan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
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

@BindingAdapter("discountRate")
fun TextView.setDiscount(disRate: Float?){
    disRate?.let {
        text = String.format("discount rate: %.1f%%", disRate*100)
    }
}

@BindingAdapter("isPlanAvailable")
fun TextView.isPlanAvailable(isAvailable: Boolean?){
    isAvailable?.let {
        text = if(isAvailable) "This plan is currently available." else "this plan is currently unavailable."
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
        val formatter = DateTimeFormatter.ofPattern("d MMM yyyy")
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
fun bindOwnerLogo(ownerImageView : ImageView, ownerName: String){
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    val cacheDir = ownerImageView.context.cacheDir
    val imgCachedFile = File(cacheDir, ownerName)
    try{
        if (imgCachedFile.isFile) {
            val imgBitmap = BitmapFactory.decodeFile(imgCachedFile.absolutePath)
            ownerImageView.setImageBitmap(imgBitmap)
        } else {
            coroutineScope.launch {
                val ownerImgReq = Network.easyFlowServices.getOwnerImage(ownerName)
                if (ownerImgReq.isSuccessful && ownerImgReq.body()!!.contentLength() > 0) {
                    val imgByteStream = ownerImgReq.body()!!.byteStream()
                    val imgBitMap = BitmapFactory.decodeStream(imgByteStream)
                    ownerImageView.setImageBitmap(imgBitMap)
                    val cacheOutputStream = FileOutputStream(imgCachedFile)
                    imgBitMap.compress(Bitmap.CompressFormat.PNG, 100, cacheOutputStream)
                    cacheOutputStream.close()
                }
                else{
                    ownerImageView.setImageResource(R.drawable.ic_plan)
                }
            }
        }
    }
    catch (e: Exception){
        ownerImageView.setImageResource(R.drawable.ic_plan)
    }
}