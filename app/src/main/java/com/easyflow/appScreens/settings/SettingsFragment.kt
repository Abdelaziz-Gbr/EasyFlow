package com.easyflow.appScreens.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.easyflow.R
import com.easyflow.activities.signIn.signActivity.SignInActivity
import com.easyflow.database.UserDatabase
import com.easyflow.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding : FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        val application = requireNotNull(this.activity).application

        val userDataSource = UserDatabase.getDatabase(application).userDao()
        val ticketDataSource = UserDatabase.getDatabase(application).ticketDao()
        val tripDataSource = UserDatabase.getDatabase(application).tripDao()

        val viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        binding.buttonLogout.setOnClickListener{
            viewModel.logout(userDataSource, ticketDataSource, tripDataSource)
            val intent = Intent(application, SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        return binding.root
    }

}