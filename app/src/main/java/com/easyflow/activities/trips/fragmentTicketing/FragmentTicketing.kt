package com.easyflow.activities.trips.fragmentTicketing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.easyflow.R
import com.easyflow.database.UserDatabase
import com.easyflow.databinding.FragmentTicketingBinding

class FragmentTicketing : Fragment() {
    private lateinit var binding : FragmentTicketingBinding
    private lateinit var viewModel: FragmentTicketingViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ticketing, container, false)
        val viewModelFactory = FragmentTicketingViewModelFactory(UserDatabase.getDatabase(requireContext()).tripDao())
        viewModel = ViewModelProvider(this, viewModelFactory)[FragmentTicketingViewModel::class.java]

        binding.getPasserngerCountButton.setOnClickListener {
            val passengerCount = Integer.parseInt(binding.passengerCount.text.toString())
            if(passengerCount < 1) {
                Toast.makeText(
                    requireContext(),
                    "The minimum number of passengers is 1",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                findNavController().navigate(FragmentTicketingDirections.actionFragmentTicketingToTripsFragment(passengerCount))
            }
        }

        return binding.root
    }
}