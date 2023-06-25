package com.easyflow.activities.signIn.fragmentSign

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.easyflow.R
import com.easyflow.appScreens.home.activity.HomeScreen
import com.easyflow.database.UserDatabase
import com.easyflow.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        val passwordText = binding.userPassword
        val showPassword = binding.signPasswordShow

        showPassword.setOnCheckedChangeListener{ view, isChecked ->
            if(isChecked){
                passwordText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            else {
                passwordText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
            }
            passwordText.setSelection(passwordText.text!!.length)
        }
        binding.signInButton.setOnClickListener {   signIn()  }
        binding.register.setOnClickListener{    register()    }
        binding.forgotPassword.setOnClickListener{    forgotPassword()    }
        return binding.root
    }

    private fun forgotPassword() {
        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToForgotPasswordFragment())
    }

    private fun register() {
        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToRegisterFragment())
    }

    private fun signIn() {
        val userDao = UserDatabase.getDatabase(requireContext()).userDao()
        val ticketDao = UserDatabase.getDatabase(requireContext()).ticketDao()

        val viewModelFactory = SignInViewModelFactory(userDao, ticketDao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[SignInViewModel::class.java]

        val userName = binding.username.text.toString()
        val userPassword = binding.userPassword.text.toString()

        viewModel.signIn(userName, userPassword)
        viewModel.signInResponse.observe(viewLifecycleOwner) {
                signResponse ->
            signResponse?.let {
                if(signResponse){
                    val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    with(sharedPreferences.edit()){
                        putBoolean("offline", false)
                        apply()
                    }
                    val intent = Intent(activity, HomeScreen::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                else{
                    Toast.makeText(activity, "username or password are not correct", Toast.LENGTH_LONG).show()
                }
                viewModel.onResponseReceived()
            }
        }

    }

}