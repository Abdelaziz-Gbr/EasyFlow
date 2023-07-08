package com.easyflow.activities.signIn.fragmentSign

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.easyflow.R
import com.easyflow.appScreens.home.activity.HomeScreen
import com.easyflow.cache.SharedPreferences
import com.easyflow.database.UserDatabase
import com.easyflow.databinding.FragmentSignInBinding
import com.easyflow.utils.LoadingDialog

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        val passwordText = binding.userPassword
        val showPassword = binding.signPasswordShow
        (activity as AppCompatActivity).supportActionBar?.hide()
        if(Build.VERSION.SDK_INT >= 33){
            val firstTime = SharedPreferences.data.getBoolean("first_time", true)
            if (firstTime && checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PermissionChecker.PERMISSION_GRANTED
            ) {
                requireActivity().requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
            with(SharedPreferences.data.edit()) {
                putBoolean("first_time", false)
                apply()
            }
        }
        showPassword.setOnCheckedChangeListener{ _, isChecked ->
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
        val loadingDialog = LoadingDialog(requireActivity())
        loadingDialog.startLoadingAnimation()

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
                loadingDialog.endLoadingAnimation()
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