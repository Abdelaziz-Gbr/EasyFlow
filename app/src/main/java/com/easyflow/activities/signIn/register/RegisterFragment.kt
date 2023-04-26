package com.easyflow.activities.signIn.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.easyflow.R
import com.easyflow.databinding.FragmentRegisterBinding
import com.easyflow.models.User

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel : RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        viewModel = RegisterViewModel()
        binding.registerButton.setOnClickListener {  register()   }
        return binding.root
    }

    private fun register() {
        val user = getUser()

        if (user != null) {
            viewModel.register(user)
        }
        else{
            Toast.makeText(requireContext(), "please fill all the above slots", Toast.LENGTH_LONG).show()
        }
        viewModel.registerResponse.observe(viewLifecycleOwner){ response ->
            if(response != null){
                when(response){
                    RegisterStatus.LOADING -> {
                    //todo add loading effect.
                    }
                    RegisterStatus.OK -> {
                        Toast.makeText(requireContext(), "Sign up Successful", Toast.LENGTH_LONG).show()
                        view?.findNavController()?.navigate(RegisterFragmentDirections.actionRegisterFragmentToSignInFragment())
                    }
                    else -> {
                        viewModel.registerErrorMessage.observe(viewLifecycleOwner){msg->
                            if(msg != null)Toast.makeText(requireContext(), "error: $msg", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                viewModel.onRegistered()
            }
        }

    }
    private fun getUser(): User? {
        var user: User?
        var gender = if(binding.maleRadioButton.isChecked) "M" else "F"
        //todo change this to get Date from a button on the ui.
        var birthDate = binding.birthDateRegister.getDate()
        if(binding.firstNameRegister.text.isEmpty() ||
            binding.lastNameRegister.text.isEmpty() ||
            binding.userNameRegister.text.isEmpty() ||
            binding.emailRegister.text.isEmpty() ||
            binding.passwordRegister.text.isEmpty() ||
            binding.phoneNumberRegister.text.isEmpty())
        {
            Toast.makeText(requireContext(), "please fill all the info above.", Toast.LENGTH_SHORT).show()
            return null
        }
        user = User(
            null,
            binding.firstNameRegister.text.toString(),
            binding.lastNameRegister.text.toString(),
            binding.userNameRegister.text.toString(),
            binding.emailRegister.text.toString(),
            binding.passwordRegister.text.toString(),
            binding.phoneNumberRegister.text.toString(),
            gender,
            birthDate
        )
        return user

    }
    private fun DatePicker.getDate(): String {
        return "$year-$month-$dayOfMonth"

    }

}