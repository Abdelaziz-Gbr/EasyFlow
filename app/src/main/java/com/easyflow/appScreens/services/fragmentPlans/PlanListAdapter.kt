package com.easyflow.appScreens.services.fragmentPlans

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easyflow.databinding.PlanListItemBinding
import com.easyflow.network.models.PlanNetworkModel

class PlanListAdapter(private val onClickListener: OnClickListener) : ListAdapter<PlanNetworkModel, PlanListAdapter.PlanItem>(DiffCallback) {
    class PlanItem(private var binding: PlanListItemBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind(planNetworkModel: PlanNetworkModel, onClickListener: OnClickListener){
            binding.plan = planNetworkModel
            binding.planViewDetailsBtn.setOnClickListener { onClickListener.onClick(planNetworkModel) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PlanItem {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PlanListItemBinding.inflate(layoutInflater, parent, false)

                return PlanItem(binding)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlanNetworkModel>() {
        override fun areItemsTheSame(
            oldItem: PlanNetworkModel,
            newItem: PlanNetworkModel
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: PlanNetworkModel,
            newItem: PlanNetworkModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanItem {
        return PlanItem.from(parent)
    }

    override fun onBindViewHolder(holder: PlanItem, position: Int) {
        val itemPlan = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(itemPlan)
        }
        holder.bind(itemPlan, onClickListener)
    }

    class OnClickListener(val clickListener: (plan: PlanNetworkModel) -> Unit){
        fun onClick(plan: PlanNetworkModel) = clickListener(plan)
    }
}