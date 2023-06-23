package com.easyflow.activities.signIn.fragmentSign

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.easyflow.R
import com.easyflow.activities.homeScreen.HomeScreen
import com.easyflow.activities.signIn.fragmentSign.SignInFragmentDirections
import com.easyflow.database.UserDatabase
import com.easyflow.databinding.FragmentSignInBinding
import com.easyflow.database.models.UserDatabaseModel

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
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
        val dataSource = UserDatabase.getDatabase(requireContext()).userDao()
        val viewModelFactory = SignInViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)[SignInViewModel::class.java]
        val userName = binding.username.text.toString()
        val userPassword = binding.userPassword.text.toString()

        viewModel.signIn(userName, userPassword)
        viewModel.signInResponse.observe(viewLifecycleOwner) {
                signResponse ->
            if (signResponse != null) {
                if(signResponse){
                    if(binding.staySignedInCheck.isChecked){
                        viewModel.addUser(UserDatabaseModel(id = 0, username = userName, password = userPassword))
                    }
                    val intent = Intent(activity, HomeScreen::class.java)
                    startActivity(intent)
                    val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    with(sharedPreferences.edit()){
                        putBoolean("offline", false)
                        apply()
                    }
                    activity?.finish()
                }
                else {
                    Toast.makeText(activity, "username or password are not correct", Toast.LENGTH_LONG).show()
                }
                viewModel.onResponseReceived()
            }
        }

    }

}