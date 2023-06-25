package com.easyflow.activities.services.fragmentRecharge

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

class RechargeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentRechargeBinding>(inflater, R.layout.fragment_recharge, container, false)
        val viewModel = ViewModelProvider(this)[RechargeFragmentViewModel::class.java]
        binding.rechargeButton.setOnClickListener{
            val amount = binding.rechargeAmount.text.toString().toFloat()
            viewModel.recharge(amount)
            //todo view ProgressBar
        }
        viewModel.rechargeStatus.observe(viewLifecycleOwner){
            succeeded ->
            if(succeeded != null){
                if(succeeded)
                {
                    Toast.makeText(requireContext(), "Recharge Succeeded!", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(requireContext(), "Recharge Failed!", Toast.LENGTH_LONG).show()
                }
                viewModel.onRechargeStatusReceived()
            }
        }
        return binding.root
    }

}