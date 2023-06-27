package com.easyflow.appScreens.services.fragmentPlans.planDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easyflow.R
import com.easyflow.databinding.FragmentPlanDetailsBinding

class PlanDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPlanDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlanDetailsBinding.inflate(inflater)
        val selectedPlan = PlanDetailsFragmentArgs.fromBundle(requireArguments()).selectedPlan
        binding.plan = selectedPlan
        return binding.root
    }
}