package com.easyflow.activities.signIn.fragmentForgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        (activity as AppCompatActivity).supportActionBar?.show()
        val viewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.forgotButton.setOnClickListener{
            val email = binding.forgotpasswordEmail.text.toString()
            viewModel.sendUserReset(email)
            binding.forgotPasswordTextView.visibility = View.VISIBLE
        }
        viewModel.emailFound.observe(viewLifecycleOwner){msg->
            msg?.let {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                viewModel.onMsgRecieved()
            }
        }
        return binding.root
    }

}