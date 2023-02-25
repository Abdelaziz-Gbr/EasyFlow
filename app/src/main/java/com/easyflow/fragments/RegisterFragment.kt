package com.easyflow.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.easyflow.BuildConfig
import com.easyflow.R
import com.easyflow.databinding.FragmentRegisterBinding
import com.easyflow.models.ServerResponse
import com.easyflow.models.User
import com.easyflow.repository.NetworkRepository
import com.easyflow.viewModel.NetworkViewModel
import com.easyflow.viewModel.UserDatabaseViewModel
import com.easyflow.viewModelFactory.NetworkViewModelFactory
import com.google.gson.Gson

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var databaseViewModel: UserDatabaseViewModel
    private lateinit var networkViewModel : NetworkViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        databaseViewModel = ViewModelProvider(this)[UserDatabaseViewModel::class.java]
        binding.registerButton.setOnClickListener {  register(it)   }
        return binding.root
    }

    private fun register(view: View?) {
        val networkRepository = NetworkRepository()
        val networkViewModelFactory = NetworkViewModelFactory(networkRepository)
        networkViewModel = ViewModelProvider(this, networkViewModelFactory)[NetworkViewModel::class.java]

        val user = getUser(view)

        if (user != null) {
            networkViewModel.register(user)
        }
        else{
            Toast.makeText(requireContext(), "please fill all the above slots", Toast.LENGTH_LONG).show()
        }
        networkViewModel.registerResponse.observe(viewLifecycleOwner){ response ->
            if(response.isSuccessful){
                Toast.makeText(requireContext(), "Sign up Successful", Toast.LENGTH_LONG).show()
                if (BuildConfig.DEBUG) Log.d("register_success", response.message())

                view?.findNavController()?.navigate(RegisterFragmentDirections.actionRegisterFragmentToSignInFragment())


            }
            else{
                var errorResponse: ServerResponse? = Gson().fromJson(response.errorBody()?.charStream(), ServerResponse::class.java)
               Toast.makeText(requireContext(), "error signing up ${errorResponse?.message!!}", Toast.LENGTH_LONG).show()

            }
        }

    }
    private fun getUser(view: View?): User? {
        var user: User?
        var gender = if(binding.maleRadioButton.isChecked) "M" else "F"
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
        user = User(null, binding.firstNameRegister.text.toString(), binding.lastNameRegister.text.toString(),
            binding.userNameRegister.text.toString(), binding.emailRegister.text.toString(),
            binding.passwordRegister.text.toString(), binding.phoneNumberRegister.text.toString(),
            gender, birthDate
        )
        return user

    }

}
private fun DatePicker.getDate(): String {
    return "$year-$month-$dayOfMonth"

}