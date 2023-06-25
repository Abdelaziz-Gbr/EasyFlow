package com.easyflow.appScreens.home.fragmentHistory.ticketDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.easyflow.R
import com.easyflow.database.UserDatabase
import com.easyflow.databinding.FragmentTicketDetailBinding

class TicketDetail : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTicketDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_ticket_detail, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = TicketDetailArgs.fromBundle(requireArguments())

        val dataSource = UserDatabase.getDatabase(application).ticketDao()
        Log.d("ticket_id", arguments.ticketId)
        val viewModelFactory = TicketDetailViewModelFactory(arguments.ticketId, dataSource)

        val viewModel = ViewModelProvider(this, viewModelFactory)[TicketDetailViewModel::class.java]

        binding.viewModel = viewModel

        binding.lifecycleOwner = this


        return binding.root
    }

}