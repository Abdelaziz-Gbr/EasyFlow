package com.easyflow.activities.trips.fragmentTrips

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
import com.easyflow.databinding.FragmentTripsBinding

class TripsFragment : Fragment() {
    private lateinit var binding : FragmentTripsBinding
    private lateinit var viewModel: TripsFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trips, container, false)

        val arguments = TripsFragmentArgs.fromBundle(requireArguments())
        val passengersCount = arguments.count

        val tripsDataSource = UserDatabase.getDatabase(requireContext()).tripDao()
        val viewModelFactory = TripsFragmentViewModelFactory(tripsDataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[TripsFragmentViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.tripID.observe(viewLifecycleOwner){tripId ->
            tripId?.let {
                findNavController().navigate(TripsFragmentDirections.actionTripsFragmentToQRCodeFragment(tripID = tripId, count = passengersCount))
                viewModel.onTripNavigated()
            }
        }

        return binding.root


    }
}