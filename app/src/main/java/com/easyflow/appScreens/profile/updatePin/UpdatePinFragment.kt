package com.easyflow.appScreens.profile.updatePin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.easyflow.databinding.FragmentUpdatePinBinding
import com.easyflow.utils.LoadingDialog

class UpdatePinFragment : Fragment() {
    private lateinit var binding: FragmentUpdatePinBinding
    private lateinit var viewModel: UpdatePinViewModel
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdatePinBinding.inflate(inflater)
        loadingDialog = LoadingDialog(requireActivity())
        viewModel = ViewModelProvider(this)[UpdatePinViewModel::class.java]
        binding.updatePinBtn.setOnClickListener {
            loadingDialog.startLoadingAnimation()
            val newPin = binding.pinText.text.toString()
            viewModel.updatePin(newPin)
        }
        viewModel.response.observe(viewLifecycleOwner){msg->
            msg?.let {
                loadingDialog.endLoadingAnimation()
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                viewModel.onMsgRecieved()
            }
        }
        return binding.root
    }

}