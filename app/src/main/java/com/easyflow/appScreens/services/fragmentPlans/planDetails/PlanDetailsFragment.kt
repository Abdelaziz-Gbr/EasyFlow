package com.easyflow.appScreens.services.fragmentPlans.planDetails

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.easyflow.databinding.FragmentPlanDetailsBinding
import com.easyflow.utils.ApiCallStatus

class PlanDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPlanDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlanDetailsBinding.inflate(inflater)
        val selectedPlan = PlanDetailsFragmentArgs.fromBundle(requireArguments()).selectedPlan
        binding.plan = selectedPlan

        val viewModel = ViewModelProvider(this)[PlanDetailsFragmentViewModel::class.java]

        binding.subToPlanBtn.setOnClickListener {  viewModel.subscripeToPlan(selectedPlan)}
        viewModel.subscribeCallStatus.observe(viewLifecycleOwner){status->
            when(status){
                ApiCallStatus.DONE->{
                    val builder = AlertDialog.Builder(requireContext())
                    with(builder){
                        setMessage("Thank you for subscribing! Please check your email for updates.")
                        setPositiveButton("OK"){_ , _ ->
                            viewModel.onResponseRecieved()
                        }
                        show()
                    }
                }
                ApiCallStatus.ERROR->{
                    Toast.makeText(requireContext(), "Error happened, please try again later", Toast.LENGTH_SHORT).show()
                }
                else->{
                    //todo
                    //maybe show a loading spinner or do nothing at all as that would freeze the screen till the response recieved.
                    //but if you do, don't forget to handle LOADING seperatly from null.
                }
            }

        }
        return binding.root
    }
}