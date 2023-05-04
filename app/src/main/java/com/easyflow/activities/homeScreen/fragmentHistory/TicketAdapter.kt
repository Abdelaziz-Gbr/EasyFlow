package com.easyflow.activities.homeScreen.fragmentHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easyflow.R
import com.easyflow.database.models.TicketDatabaseModel
import com.easyflow.utils.convertLongToTime

class TicketAdapter: RecyclerView.Adapter<TicketAdapter.ViewHolder>() {
    var data = listOf<TicketDatabaseModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount() = data.size

    class ViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        val timeText = itemView.findViewById<TextView>(R.id.ticket_item_time)
        val placeText = itemView.findViewById<TextView>(R.id.ticket_item_place)
        val priceText = itemView.findViewById<TextView>(R.id.ticket_item_price)

        fun bind(item: TicketDatabaseModel) {
            timeText.text = convertLongToTime(item.startTime)
            placeText.text = "from ${item.startStation} to ${item.endStation}"
            priceText.text = "${item.price} EGP."
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.ticket_history_list_item, parent, false)

                return ViewHolder(view)
            }
        }

    }
}