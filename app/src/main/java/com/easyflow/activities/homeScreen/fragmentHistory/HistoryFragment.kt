package com.easyflow.activities.homeScreen.fragmentHistory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.easyflow.R
import com.easyflow.database.UserDatabase
import com.easyflow.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHistoryBinding>(
            inflater,
            R.layout.fragment_history,
            container,
            false)

        val ticketDataSource = UserDatabase.getDatabase(requireContext()).ticketDao()
        val viewModelFactory = HistoryFragmentViewModelFactory(ticketDataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)[HistoryFragmentViewModel::class.java]

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        val adapter = TicketAdapter()
        binding.ticketList.adapter = adapter

        viewModel.tickets.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        return binding.root
    }

}