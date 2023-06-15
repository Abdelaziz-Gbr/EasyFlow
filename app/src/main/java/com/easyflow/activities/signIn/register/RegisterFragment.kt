package com.easyflow.activities.signIn.register

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
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
import com.easyflow.network.models.UserNetworkModel
import java.text.SimpleDateFormat
import java.util.Calendar

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel : RegisterViewModel
    private var userHasPickedDate : Boolean = false
    private var cal: Calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        viewModel = RegisterViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val dateSetListener = DatePicker.OnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            userHasPickedDate = true
        }

        val dateRegister = binding.birthDateRegister

        dateRegister.init(dateRegister.year, dateRegister.month, dateRegister.dayOfMonth, dateSetListener)


        val passwordText = binding.passwordRegister
        val showPassword = binding.cbShowPassword

        showPassword.setOnCheckedChangeListener{ view, isChecked ->
            if(isChecked){
                passwordText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            else {
                passwordText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
            }
            passwordText.setSelection(passwordText.text!!.length)
        }
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
    private fun getUser(): UserNetworkModel? {
        var user: UserNetworkModel?
        var gender = if (binding.maleRadioButton.isChecked) "M" else "F"
        var birthDate = SimpleDateFormat("yyyy-MM-dd").format(cal.time)
        Log.d("birthDate", birthDate)
        if (checkForValidInput()) {
            Toast.makeText(requireContext(), "please fill all the info above.", Toast.LENGTH_SHORT)
                .show()
            return null
        }
        user = UserNetworkModel(
            firstName = binding.firstNameRegister.text.toString(),
            lastName = binding.lastNameRegister.text.toString(),
            username = binding.userNameRegister.text.toString(),
            email = binding.emailRegister.text.toString(),
            password = binding.passwordRegister.text.toString(),
            phoneNumber = binding.phoneNumberRegister.text.toString(),
            gender = gender,
            birthDay = birthDate
        )
        return user

    }

    private fun checkForValidInput(): Boolean {
        return  binding.firstNameRegister.text!!.isEmpty() ||
                binding.lastNameRegister.text!!.isEmpty() ||
                binding.userNameRegister.text!!.isEmpty() ||
                binding.emailRegister.text!!.isEmpty() ||
                binding.passwordRegister.text!!.isEmpty() ||
                binding.phoneNumberRegister.text!!.isEmpty()||
                !userHasPickedDate
    }

}