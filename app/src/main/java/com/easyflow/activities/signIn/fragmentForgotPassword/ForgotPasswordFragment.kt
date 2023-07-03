package com.easyflow.activities.signIn.fragmentForgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.easyflow.R
import com.easyflow.databinding.FragmentForgotPasswordBinding
import com.easyflow.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        val viewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.forgotButton.setOnClickListener{
            //todo send the username to the server to process password recovery
            val email = binding.forgotpasswordEmail.text.toString().replace("\"", "")
            viewModel.sendUserReset(email)
            binding.forgotPasswordTextView.visibility = View.VISIBLE
        }
        return binding.root
    }

}