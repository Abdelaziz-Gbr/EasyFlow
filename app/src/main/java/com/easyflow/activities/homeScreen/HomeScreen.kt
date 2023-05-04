package com.easyflow.activities.homeScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.easyflow.R
import com.easyflow.databinding.ActivityHomeScreenBinding

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityHomeScreenBinding>(this, R.layout.activity_home_screen)
        val navController = this.findNavController(R.id.HomeNavHostFragment)

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.HomeNavHostFragment)
        return navController.navigateUp()
    }
}