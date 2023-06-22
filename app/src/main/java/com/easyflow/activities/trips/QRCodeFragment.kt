package com.easyflow.activities.trips

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.easyflow.R
import com.easyflow.databinding.FragmentQRCodeBinding
import com.easyflow.tickitingApplications.QRCodeGenerator

class QRCodeFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentQRCodeBinding>(inflater, R.layout.fragment_q_r_code, container, false)
        val argument = QRCodeFragmentArgs.fromBundle(requireArguments())
        val tripID = argument.tripID
        val count = argument.count
        binding.qrImage.setImageBitmap(QRCodeGenerator().getQrCode(tripID = tripID, passengerCount = count))
        return binding.root
    }

}