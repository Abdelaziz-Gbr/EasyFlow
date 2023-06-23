package com.easyflow.activities.signIn.signActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.easyflow.R
import com.easyflow.activities.signIn.fragmentSign.SignInFragment
import com.easyflow.activities.trips.TripsActivity
import com.easyflow.database.UserDatabase
import com.easyflow.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        binding = DataBindingUtil.setContentView<ActivitySignInBinding>(this, R.layout.activity_sign_in)
        val tripDao = UserDatabase.getDatabase(this).tripDao()
        val viewModelFactory = SignActivityViewModelFactory(tripDao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[SignActivityViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.noInternetText.setOnClickListener {
            val sp = this.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            with(sp.edit()){
                putBoolean("offline", true)
            }
            val intent = Intent(this, TripsActivity::class.java)
            startActivity(intent)
            finish()
        }

        navController = this.findNavController(R.id.SignNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.signInFragment) {
                viewModel.onSignScreenOnFocus()
            }
            else{
                viewModel.onSignScreenoffFocus()
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}