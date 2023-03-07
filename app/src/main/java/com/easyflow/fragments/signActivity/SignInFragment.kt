package com.easyflow.fragments.signActivity

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
import com.easyflow.activities.HomeScreen
import com.easyflow.cache.UserCache
import com.easyflow.databinding.FragmentSignInBinding
import com.easyflow.models.User
import com.easyflow.repository.NetworkRepository
import com.easyflow.viewModel.NetworkViewModel
import com.easyflow.viewModel.UserDatabaseViewModel
import com.easyflow.viewModelFactory.NetworkViewModelFactory

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
        findNavController()?.navigate(SignInFragmentDirections.actionSignInFragmentToRegisterFragment())
    }

    private fun signIn() {
        val networkRepository = NetworkRepository()
        val networkViewModelFactory = NetworkViewModelFactory(networkRepository)
        val networkViewModel : NetworkViewModel = ViewModelProvider(
            this,
            networkViewModelFactory
        )[NetworkViewModel::class.java]
        val userName = binding.username.text.toString()
        val userPassword = binding.userPassword.text.toString()
        //todo split network User model from DB User model.
        var user = User(id = null, username = userName, password = userPassword)

        networkViewModel.signIn(user)
        networkViewModel.loginResponse.observe(viewLifecycleOwner) {
                signResponse ->
            if (signResponse) {
                if(binding.staySignedInCheck.isChecked){
                    val databaseViewModel = ViewModelProvider(this)[UserDatabaseViewModel::class.java]
                    //todo if not the other info have use saved on the DB just remove them from the model.
                    user.id = 0
                    databaseViewModel.addUser(user)
                }
                val intent = Intent(activity, HomeScreen::class.java)
                startActivity(intent)
                activity?.finish()
            }
            else {
                Toast.makeText(activity, "username or password are not correct", Toast.LENGTH_LONG).show()
            }
        }

    }

}