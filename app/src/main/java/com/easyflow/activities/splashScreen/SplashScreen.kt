package com.easyflow.activities.splashScreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.easyflow.R
import com.easyflow.activities.homeScreen.HomeScreen
import com.easyflow.activities.signIn.signActivity.SignInActivity
import com.easyflow.activities.trips.TripsActivity
import com.easyflow.database.UserDatabase
import com.easyflow.databinding.ActivitySplashScreenBinding

//todo maybe the splash screen is not relative anymore and should be replaced with the main activity?
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //initialize the view
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splash_screen)
        val binding = DataBindingUtil.setContentView<ActivitySplashScreenBinding>(
            this,
            R.layout.activity_splash_screen
        )
        val userDao = UserDatabase.getDatabase(this.application).userDao()
        val tripsDao = UserDatabase.getDatabase(this).tripDao()

        val viewModelFactory = SplashScreenViewModelFactory(userDao, tripsDao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[SplashScreenViewModel::class.java]

        binding.efLogo.alpha = 0f
        viewModel.getUser()

        binding.efLogo.animate().setDuration(500).alpha(0.75f)

        viewModel.navigateTo.observe(this){ navigate ->
            navigate?.let {
                binding.efLogo.animate().setDuration(1).alpha(1f).withEndAction{
                    var intent = Intent(
                        this,
                            when(navigate){
                                2 -> HomeScreen::class.java
                                3 -> TripsActivity::class.java
                                else -> SignInActivity::class.java
                            }
                        )
                    val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    with(sharedPreferences.edit())
                    {
                        putBoolean("offline", navigate != 2)
                        apply()
                    }
                    if(navigate == 3)
                        Toast.makeText(this, "Starting in offline mode", Toast.LENGTH_SHORT).show()
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    startActivity(intent)
                    finish()
                }
            }
        }


    }
}