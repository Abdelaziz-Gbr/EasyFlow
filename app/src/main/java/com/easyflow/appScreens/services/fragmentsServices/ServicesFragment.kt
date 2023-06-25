package com.easyflow.appScreens.services.fragmentsServices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.easyflow.databinding.FragmentServicesBinding

class ServicesFragment : Fragment() {
    private lateinit var binding : FragmentServicesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicesBinding.inflate(inflater)
        binding.rechargeBtn.setOnClickListener {
            findNavController().navigate(ServicesFragmentDirections.actionServicesFragmentToRechargeFragment())
        }
        binding.viewAllPlansBtn.setOnClickListener {
            findNavController().navigate(ServicesFragmentDirections.actionServicesFragmentToPlansFragment())
        }
        binding.viewPlanBtn.setOnClickListener {
            Toast.makeText(requireContext(), "on work", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

}