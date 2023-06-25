package com.easyflow.appScreens.home.fragmentHistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

        val adapter = TicketAdapter(TicketAdapter.TicketItemListener { ticketId ->
            viewModel.onTicketClicked(ticketId)
        })
        binding.ticketList.adapter = adapter

        viewModel.tickets.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        viewModel.navigateToTicketView.observe(viewLifecycleOwner){ ticketId ->
            if(ticketId != null){
            this.findNavController().navigate(
                HistoryFragmentDirections.actionHistoryFragmentToTicketDetail(
                    ticketId
                )
            )
            viewModel.onTicketViewNavigated()
            }
        }

        return binding.root
    }

}