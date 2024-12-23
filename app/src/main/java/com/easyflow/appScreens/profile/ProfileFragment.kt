package com.easyflow.appScreens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.easyflow.R
import com.easyflow.databinding.FragmentProfileBinding
import com.easyflow.network.models.ProfileUpdateNetworkModel
import com.easyflow.utils.LoadingDialog

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        loadingDialog = LoadingDialog(requireActivity())
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.updateResponse.observe(viewLifecycleOwner){response ->
            response?.let {
                loadingDialog.endLoadingAnimation()
                Toast.makeText(requireContext(), response, Toast.LENGTH_LONG).show()
                viewModel.onUpdateResponseReceived()
            }
        }

        binding.saveButton.setOnClickListener {
            loadingDialog.startLoadingAnimation()
            val firstName = binding.profileFirstname.text.toString()
            val lastName = binding.profileLastname.text.toString()
            val email = binding.profileEmail.text.toString()
            val phoneNumber = binding.profilePhone.text.toString()
            val gender = if(binding.maleRadioButton.isChecked) "M" else "F"
            val profileUpdateNetworkModel = ProfileUpdateNetworkModel(firstName, lastName, email, phoneNumber, gender)
            viewModel.updateProfile(profileUpdateNetworkModel)
        }

        binding.changePasswordBtn.setOnClickListener { findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToUpdatePasswordFragment()) }
        binding.updatePinBtn.setOnClickListener { findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToUpdatePinFragment()) }
        return binding.root
    }
}