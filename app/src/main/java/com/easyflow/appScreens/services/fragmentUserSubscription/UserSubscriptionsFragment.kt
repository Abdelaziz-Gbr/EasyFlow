package com.easyflow.appScreens.services.fragmentUserSubscription

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.easyflow.R
import com.easyflow.databinding.FragmentUserSubscriptionsBinding

class UserSubscriptionsFragment : Fragment() {
    private lateinit var binding : FragmentUserSubscriptionsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_subscriptions,
            container,
            false
        )
        val viewModel = ViewModelProvider(this)[UserSubscriptionsFragmentViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.userSubscriptionsRv.adapter = SubscriptionsListAdapter(SubscriptionsListAdapter.ManageSubscriptionClickListener {userPlane ->
            viewModel.navigateToManagePlanClicked(userPlane)
        })
        viewModel.managePlan.observe(viewLifecycleOwner){subscription->
            subscription?.let {
                findNavController().navigate(UserSubscriptionsFragmentDirections.actionUserSubscriptionsFragmentToSubscriptionManagmentFragment(subscription))
                viewModel.onManagePlanNavigated()
            }
        }
        viewModel.error.observe(viewLifecycleOwner){error ->
            error?.let {
                Toast.makeText(requireContext(), "an error occurred please try again later.", Toast.LENGTH_SHORT).show()
                viewModel.onErrorRecieved()
            }
        }
        return binding.root
    }
}