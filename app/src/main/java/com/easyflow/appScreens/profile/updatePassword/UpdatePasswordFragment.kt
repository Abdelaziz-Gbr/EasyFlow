package com.easyflow.appScreens.profile.updatePassword

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.easyflow.R
import com.easyflow.databinding.FragmentUpdatePasswordBinding

class UpdatePasswordFragment : Fragment() {
    private lateinit var binding : FragmentUpdatePasswordBinding
    private lateinit var viewModel: UpdatePasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_update_password,
            container,
            false)
        viewModel = ViewModelProvider(this)[UpdatePasswordViewModel::class.java]

        val oldPassword = binding.oldPassword
        val newPassword = binding.newPassword
        val newPasswordConfrimation = binding.newPasswordReconferm
        val showPassword = binding.updatePasswordShow

        showPassword.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked) {
                oldPassword.inputType = InputType.TYPE_CLASS_TEXT
                newPassword.inputType = InputType.TYPE_CLASS_TEXT
                newPasswordConfrimation.inputType = InputType.TYPE_CLASS_TEXT
            }
            else{
                oldPassword.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                newPassword.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                newPasswordConfrimation.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
            }
        }

        binding.saveNewPasswordBtn.setOnClickListener {
            if(newPassword.text.toString() != newPasswordConfrimation.text.toString()){
                Toast.makeText(requireContext(), "new password should be equal to password Confirmation.", Toast.LENGTH_SHORT).show()
            }
            else{
                viewModel.updatePassword(oldPassword.text.toString(), newPassword.text.toString(), newPasswordConfrimation.text.toString())
            }
        }
        viewModel.res.observe(viewLifecycleOwner){msg->
            msg?.let {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                viewModel.onMsgRecieved()
            }
        }
        return binding.root
    }
}