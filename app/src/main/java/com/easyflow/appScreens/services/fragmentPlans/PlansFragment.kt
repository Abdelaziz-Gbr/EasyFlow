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
import com.easyflow.utils.EasyFlowApiStatus
import com.easyflow.utils.LoadingDialog

class PlansFragment : Fragment() {
    private lateinit var binding : FragmentPlansBinding
    private lateinit var viewModel: PlansFragmentViewModel
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_plans,container, false)

        viewModel = ViewModelProvider(this)[PlansFragmentViewModel::class.java]

        loadingDialog = LoadingDialog(requireActivity())

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.plansRv.adapter = PlanListAdapter(PlanListAdapter.OnClickListener {
            viewModel.displayPlanDetails(it)
        })

        viewModel.status.observe(viewLifecycleOwner){ApiCallState ->
            ApiCallState?.let {
                when(ApiCallState){
                    EasyFlowApiStatus.LOADING->{
                        loadingDialog.startLoadingAnimation()
                    }
                    EasyFlowApiStatus.DONE->{
                        loadingDialog.endLoadingAnimation()
                    }
                    EasyFlowApiStatus.ERROR->{
                        loadingDialog.endLoadingAnimation()
                        Toast.makeText(
                            requireContext(),
                            "Failed to Retrieve Plans information, pls try again later",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                viewModel.onStatusRecieved()

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