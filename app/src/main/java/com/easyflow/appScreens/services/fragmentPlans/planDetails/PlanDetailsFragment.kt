package com.easyflow.appScreens.services.fragmentPlans.planDetails

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.easyflow.databinding.FragmentPlanDetailsBinding
import com.easyflow.utils.LoadingDialog

class PlanDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPlanDetailsBinding
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlanDetailsBinding.inflate(inflater)
        val selectedPlan = PlanDetailsFragmentArgs.fromBundle(requireArguments()).selectedPlan
        loadingDialog = LoadingDialog(requireActivity())
        binding.plan = selectedPlan

        val viewModel = ViewModelProvider(this)[PlanDetailsFragmentViewModel::class.java]

        binding.subToPlanBtn.setOnClickListener {
            loadingDialog.startLoadingAnimation()
            viewModel.subscripeToPlan(selectedPlan)
        }
        viewModel.planSubscripe.observe(viewLifecycleOwner){ response->
            response?.let{
                loadingDialog.endLoadingAnimation()
                val builder = AlertDialog.Builder(requireContext())
                with(builder) {
                    setMessage(response)
                    setPositiveButton("OK") { _, _ ->
                        viewModel.onResponseRecieved()
                    }
                    show()
                }
            }

        }
        return binding.root
    }
}