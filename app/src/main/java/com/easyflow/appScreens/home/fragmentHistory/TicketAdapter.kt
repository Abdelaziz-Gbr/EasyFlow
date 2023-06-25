package com.easyflow.appScreens.home.fragmentHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easyflow.database.models.TicketDatabaseModel
import com.easyflow.databinding.TicketHistoryListItemBinding

class TicketAdapter(val ticketItemListener: TicketItemListener): ListAdapter<TicketDatabaseModel, TicketAdapter.ViewHolder>(
    TicketDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, ticketItemListener)
    }

    class ViewHolder private constructor(val binding: TicketHistoryListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(ticket: TicketDatabaseModel, ticketItemListener: TicketItemListener) {
            binding.ticket = ticket
            binding.clickListener = ticketItemListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TicketHistoryListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }

    class TicketDiffCallback : DiffUtil.ItemCallback<TicketDatabaseModel> (){
        override fun areItemsTheSame(
            oldItem: TicketDatabaseModel,
            newItem: TicketDatabaseModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TicketDatabaseModel,
            newItem: TicketDatabaseModel
        ): Boolean {
            return oldItem == newItem
        }

    }
    class TicketItemListener(val clickListener: (ticketId: String) -> Unit){
        fun onClick(ticket: TicketDatabaseModel) = clickListener(ticket.id)
    }
}