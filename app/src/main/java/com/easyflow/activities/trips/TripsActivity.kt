package com.easyflow.activities.trips

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.easyflow.R
import com.easyflow.activities.splashScreen.SplashScreen
import com.easyflow.cache.SharedPreferences
import com.easyflow.databinding.ActivityTripsBinding

class TripsActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityTripsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_trips)

        val sharedPreferences = SharedPreferences.data
        val offline = sharedPreferences.getBoolean("offline", false)

        binding.homeImg.setOnClickListener {
            if(offline){
                val intent = Intent(this, SplashScreen::class.java)
                startActivity(intent)
            }
            finish()
        }
        navController = findNavController(R.id.trip_view_fragment)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}