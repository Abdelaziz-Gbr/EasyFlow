package com.easyflow.appScreens.services.fragmentRecharge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.easyflow.R
import com.easyflow.databinding.FragmentRechargeBinding
import com.easyflow.utils.LoadingDialog

class RechargeFragment : Fragment() {
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentRechargeBinding>(inflater, R.layout.fragment_recharge, container, false)
        val viewModel = ViewModelProvider(this)[RechargeFragmentViewModel::class.java]
        loadingDialog = LoadingDialog(requireActivity())
        binding.rechargeButton.setOnClickListener{
            val amount = binding.rechargeAmount.text.toString().toFloat()
            if(amount < 1){
                Toast.makeText(requireContext(), "amount to recharge should be more than 0.",Toast.LENGTH_SHORT).show()
            }
            else{
                loadingDialog.startLoadingAnimation()
                viewModel.recharge(amount)
            }
    }

        viewModel.rechargeStatus.observe(viewLifecycleOwner){
                succeeded ->
            succeeded?.let{
                loadingDialog.endLoadingAnimation()
                if(succeeded)
                {
                    Toast.makeText(requireContext(), "Recharge Succeeded!", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(requireContext(), "Recharge Failed!", Toast.LENGTH_SHORT).show()
                }
                viewModel.onRechargeStatusReceived()
            }
        }
        return binding.root
}}