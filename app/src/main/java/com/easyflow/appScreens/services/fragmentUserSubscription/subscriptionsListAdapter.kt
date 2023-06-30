package com.easyflow.appScreens.services.fragmentUserSubscription

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easyflow.databinding.UserSubscriptionListItemBinding
import com.easyflow.network.models.UserPlan

class subscriptionsListAdapter: ListAdapter<UserPlan, subscriptionsListAdapter.SubscriptionListItem>(DiffCallback) {
    class SubscriptionListItem(
        private var binding: UserSubscriptionListItemBinding)
        :RecyclerView.ViewHolder(binding.root)
    {
            fun bind(userPlan: UserPlan){
                binding.subscription = userPlan
                binding.executePendingBindings()
            }
        companion object{
            fun from(parent: ViewGroup): SubscriptionListItem{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = UserSubscriptionListItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false)

                return SubscriptionListItem(binding)
            }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<UserPlan>(){
        override fun areItemsTheSame(oldItem: UserPlan, newItem: UserPlan): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: UserPlan, newItem: UserPlan): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubscriptionListItem {
        return SubscriptionListItem.from(parent)
    }

    override fun onBindViewHolder(holder: SubscriptionListItem, position: Int) {
        val sub = getItem(position)
        holder.bind(sub)
    }
}