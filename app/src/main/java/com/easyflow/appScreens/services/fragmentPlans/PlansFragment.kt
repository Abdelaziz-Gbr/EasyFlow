package com.easyflow.appScreens.services.fragmentPlans

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
import com.easyflow.databinding.FragmentPlansBinding

class PlansFragment : Fragment() {
    private lateinit var binding : FragmentPlansBinding
    private lateinit var viewModel: PlansFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_plans,container, false)

        viewModel = ViewModelProvider(this)[PlansFragmentViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.plansRv.adapter = PlanListAdapter(PlanListAdapter.OnClickListener {
            viewModel.displayPlanDetails(it)
        })

        viewModel.status.observe(viewLifecycleOwner){error ->
            error?.let {
                if(error == EasyFlowApiStatus.ERROR) {
                    Toast.makeText(
                        requireContext(),
                        "Failed to Retrieve Plans information, pls try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.navigateToPlanDetail.observe(viewLifecycleOwner){
            it?.let {
                findNavController().navigate(PlansFragmentDirections.actionPlansFragmentToPlanDetailsFragment(it))
                viewModel.onPlanDetailsNavigated()
            }
        }



        return binding.root
    }
}