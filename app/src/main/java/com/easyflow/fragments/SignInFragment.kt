package com.easyflow.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.findNavController
import com.easyflow.BuildConfig
import com.easyflow.R
import com.easyflow.activities.HomeScreen
import com.easyflow.cache.UserKey
import com.easyflow.databinding.FragmentSignInBinding
import com.easyflow.models.User
import com.easyflow.repository.NetworkRepository
import com.easyflow.viewModel.NetworkViewModel
import com.easyflow.viewModel.UserDatabaseViewModel
import com.easyflow.viewModelFactory.NetworkViewModelFactory

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var databaseViewModel: UserDatabaseViewModel
    private lateinit var networkViewModel : NetworkViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        databaseViewModel = ViewModelProvider(this)[UserDatabaseViewModel::class.java]
        binding.signInButton.setOnClickListener {   signIn(it)  }
        binding.register.setOnClickListener{    register(it)    }
        return binding.root
    }

    private fun register(it: View?) {
        it?.findNavController()?.navigate(SignInFragmentDirections.actionSignInFragmentToRegisterFragment())
    }

    private fun signIn(view: View?) {
        val networkRepository = NetworkRepository()
        val networkViewModelFactory = NetworkViewModelFactory(networkRepository)
        networkViewModel = ViewModelProvider(this, networkViewModelFactory)[NetworkViewModel::class.java]

        val userName = binding.userEmail.text.toString()
        val userPassword = binding.userPassword.text.toString()
        var user = User(id = null, username = userName, password =  userPassword)

        networkViewModel.signIn(user)
        networkViewModel.signinResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful && response.headers()["Authorization"] != null) {
                //getting the auth key
                UserKey.key = response.headers()["Authorization"]!!
                //getting the rest of the user info to store in the working memory
                //todo check with haridy for how profile API works then uncomment this part.
                /*
                networkViewModel.getUserInfo()
                networkViewModel.userInfoResponse.observe(viewLifecycleOwner){ response ->
                    if(response.isSuccessful){
                        user = response.body()!!
                        user.id = 0
                        user.userKey = UserKey.key
                        if(binding.staySignedInCheck.isChecked){
                            databaseViewModel.addUser(User(username = userName, password = userPassword, userKey = UserKey.key))
                        }
                    }
                }
                */
                //todo if above part is uncommented -- remove the code till val intent is reached.
                user.userKey = UserKey.key
                if(binding.staySignedInCheck.isChecked){
                    databaseViewModel.addUser(User(username = userName, password = userPassword, userKey = UserKey.key))
                }
                //todo till here.
                val intent = Intent(activity, HomeScreen::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                Toast.makeText(activity, "username or password are not correct", Toast.LENGTH_LONG).show()
            }
        }

    }

}