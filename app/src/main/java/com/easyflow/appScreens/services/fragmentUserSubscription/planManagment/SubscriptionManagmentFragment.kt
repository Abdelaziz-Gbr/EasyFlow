package com.easyflow.appScreens.services.fragmentUserSubscription.planManagment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        viewModel.msg.observe(viewLifecycleOwner){ serverMsg ->
            serverMsg?.let {
                Toast.makeText(
                    requireContext(),
                    serverMsg,
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.onMsgRecieved()
            }
        }

        binding.renewPlanBtn.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            with(builder){
                setTitle("Renew subscription?")
                setMessage("This will remove any current remanining trips")
                setPositiveButton("Renew") { _, _ ->
                    viewModel.renewSubscription()
                }
                setNegativeButton("Cancel"){_,_ ->
                }
                show()
            }
        }

        return binding.root
    }

}