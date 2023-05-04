package com.easyflow.activities.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.easyflow.R
import com.easyflow.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentForgotPasswordBinding>(
            inflater,
            R.layout.fragment_forgot_password,
            container,
            false
        )
        binding.forgotButton.setOnClickListener{
            //todo send the username to the server to process password recovery
            binding.forgotPasswordTextView.visibility = View.VISIBLE
        }
        return binding.root
    }

}