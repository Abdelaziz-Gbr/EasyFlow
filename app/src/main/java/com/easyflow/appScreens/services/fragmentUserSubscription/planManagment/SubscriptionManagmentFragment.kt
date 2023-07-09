package com.easyflow.appScreens.services.fragmentUserSubscription.planManagment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.easyflow.R
import com.easyflow.databinding.FragmentSubscriptionManagmentBinding
import com.easyflow.utils.LoadingDialog
import com.google.android.material.snackbar.Snackbar

class SubscriptionManagmentFragment : Fragment() {

    private lateinit var binding : FragmentSubscriptionManagmentBinding
    private lateinit var viewModel : SubManagmentViewModel
    private lateinit var loadingDialog: LoadingDialog
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
                val snackbar = Snackbar.make(
                    requireView(),
                    serverMsg,
                    Snackbar.LENGTH_SHORT
                )
                val runnable = Runnable{
                    snackbar.show()
                }
                val handler = Handler(Looper.getMainLooper())
                handler.post(runnable)
                viewModel.onMsgRecieved()
            }
        }

        binding.renewPlanBtn.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            with(builder){
                setTitle("Renew subscription?")
                setMessage("This will remove any current remanining trips")
                setPositiveButton("Renew") { _, _ ->
                    loadingDialog = LoadingDialog(requireActivity())
                    loadingDialog.startLoadingAnimation()
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