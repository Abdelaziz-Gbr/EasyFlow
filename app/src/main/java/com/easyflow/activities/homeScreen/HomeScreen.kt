package com.easyflow.activities.homeScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.easyflow.R
import com.easyflow.databinding.ActivityHomeScreenBinding

class HomeScreen : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityHomeScreenBinding>(this, R.layout.activity_home_screen)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navController = this.findNavController(R.id.HomeNavHostFragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.profileFragment, R.id.settingsFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.HomeNavHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}