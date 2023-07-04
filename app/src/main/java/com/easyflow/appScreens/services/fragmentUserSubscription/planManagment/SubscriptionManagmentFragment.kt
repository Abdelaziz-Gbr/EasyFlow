package com.easyflow.appScreens.services.fragmentUserSubscription.planManagment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.easyflow.R
import com.easyflow.databinding.FragmentSubscriptionManagmentBinding

class SubscriptionManagmentFragment : Fragment() {

    private lateinit var binding : FragmentSubscriptionManagmentBinding
    private lateinit var viewModel : SubManagmentViewModel
    private val args by navArgs<SubscriptionManagmentFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_subscription_managment,
            container,
            false)

        viewModel = ViewModelProvider(this)[SubManagmentViewModel::class.java]
        viewModel.setUserSubscription(args.userPlan)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.error.observe(viewLifecycleOwner){error ->
            error?.let {
                if(error) {
                    Toast.makeText(
                        requireContext(),
                        "sorry an error occurred, please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val switch = binding.switchSubscription as SwitchCompat
                    switch.isChecked= false
                    viewModel.onErrorRecieved()
                }
            }
        }

        return binding.root
    }

}