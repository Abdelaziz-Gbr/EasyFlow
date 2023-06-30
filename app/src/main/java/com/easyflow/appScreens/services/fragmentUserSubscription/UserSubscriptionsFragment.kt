package com.easyflow.appScreens.services.fragmentUserSubscription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
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
        binding.userSubscriptionsRv.adapter = subscriptionsListAdapter()
        return binding.root
    }
}