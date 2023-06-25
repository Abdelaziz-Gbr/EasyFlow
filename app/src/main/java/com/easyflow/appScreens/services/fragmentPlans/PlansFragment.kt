package com.easyflow.appScreens.services.fragmentPlans

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.easyflow.databinding.FragmentPlansBinding

class PlansFragment : Fragment() {
    private lateinit var binding : FragmentPlansBinding
    private lateinit var viewModel: PlansFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlansBinding.inflate(inflater)

        viewModel = ViewModelProvider(this)[PlansFragmentViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.plansRv.adapter = PlanListAdapter()

        return binding.root
    }
}