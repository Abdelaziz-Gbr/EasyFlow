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
import androidx.navigation.findNavController
import com.easyflow.R
import com.easyflow.activities.HomeScreen
import com.easyflow.cache.UserCache
import com.easyflow.databinding.FragmentSignInBinding
import com.easyflow.fragments.signActivity.SignInFragmentDirections
import com.easyflow.models.User
import com.easyflow.repository.NetworkRepository
import com.easyflow.viewModel.NetworkViewModel
import com.easyflow.viewModel.UserDatabaseViewModel
import com.easyflow.viewModelFactory.NetworkViewModelFactory

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var databaseViewModel: UserDatabaseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        databaseViewModel = ViewModelProvider(this)[UserDatabaseViewModel::class.java]
        binding.signInButton.setOnClickListener {   signIn(it)  }
        binding.register.setOnClickListener{    register(it)    }
        binding.forgotPassword.setOnClickListener{    forgotPassword(it)    }
        return binding.root
    }

    private fun forgotPassword(it: View?) {
        Toast.makeText(requireContext(), "work in progress", Toast.LENGTH_SHORT).show()
    }

    private fun register(it: View?) {
        it?.findNavController()?.navigate(SignInFragmentDirections.actionSignInFragmentToRegisterFragment())
    }

    private fun signIn(view: View?) {
        val networkRepository = NetworkRepository()
        val networkViewModelFactory = NetworkViewModelFactory(networkRepository)
        val networkViewModel : NetworkViewModel = ViewModelProvider(
            this,
            networkViewModelFactory
        )[NetworkViewModel::class.java]

        val userName = binding.username.text.toString()
        val userPassword = binding.userPassword.text.toString()
        var user = User(id = null, username = userName, password = userPassword)

        var intent : Intent? = null
        networkViewModel.signIn(user)
        networkViewModel.signResponse.observe(viewLifecycleOwner) {
                signResponse ->
            if (signResponse.isSuccessful) {
                val auth = signResponse.headers()["Authorization"]
                networkViewModel.getUserInfo(auth!!)
                networkViewModel.userInfoResponse.observe(viewLifecycleOwner) {
                        infoResponse ->
                    if(infoResponse.isSuccessful){
                        user = infoResponse.body()!!
                        user.id = 0
                        user.userKey = auth
                        user.password = userPassword
                        UserCache.cacheUser(user)
                        if(binding.staySignedInCheck.isChecked){
                            databaseViewModel.addUser(user)
                        }
                        intent = Intent(activity, HomeScreen::class.java)
                    }
                    else{
                        Toast.makeText(
                            requireContext(),
                            "failed to fetch userData from the server.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    if(intent != null) {
                        startActivity(intent)
                        activity?.finish()
                    }
                }
            }
            else {
                Toast.makeText(activity, "username or password are not correct", Toast.LENGTH_LONG).show()
            }
        }

    }

}