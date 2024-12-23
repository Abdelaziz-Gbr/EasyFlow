package com.easyflow.activities.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.easyflow.R
import com.easyflow.appScreens.home.activity.HomeScreen
import com.easyflow.activities.signIn.signActivity.SignInActivity
import com.easyflow.activities.trips.TripsActivity
import com.easyflow.cache.SharedPreferences
import com.easyflow.database.UserDatabase
import com.easyflow.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //initialize the view
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splash_screen)
        val binding = DataBindingUtil.setContentView<ActivitySplashScreenBinding>(
            this,
            R.layout.activity_splash_screen
        )
        SharedPreferences.init(this)
        val userDao = UserDatabase.getDatabase(this.application).userDao()
        val ticketDao = UserDatabase.getDatabase(this).ticketDao()
        val tripDao = UserDatabase.getDatabase(this).tripDao()
        val viewModelFactory = SplashScreenViewModelFactory(userDao, ticketDao, tripDao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[SplashScreenViewModel::class.java]

        binding.efLogo.alpha = 0f


        binding.efLogo.animate().setDuration(500).alpha(0.75f)

        viewModel.navigateTo.observe(this){ navigate ->
            navigate?.let {
                binding.efLogo.animate().setDuration(1).alpha(1f).withEndAction{
                    val intent = Intent(
                        this,
                            when(navigate){
                                1 -> HomeScreen::class.java
                                2 -> TripsActivity::class.java
                                else -> SignInActivity::class.java
                            }
                        )
                    with(SharedPreferences.data.edit())
                    {
                        putBoolean("offline", navigate != 1)
                        apply()
                    }
                    if(navigate == 2)
                        Toast.makeText(this, "Starting in offline mode", Toast.LENGTH_SHORT).show()
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    startActivity(intent)
                    finish()
                }
            }
        }


    }
}