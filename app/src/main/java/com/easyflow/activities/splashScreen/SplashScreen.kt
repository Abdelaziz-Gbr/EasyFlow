package com.easyflow.activities.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.easyflow.R
import com.easyflow.activities.HomeScreen
import com.easyflow.activities.signIn.SignInActivity
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
        val dataSource = UserDatabase.getDatabase(this.application).userDao()
        val viewModelFactory = SplashScreenViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)[SplashScreenViewModel::class.java]
        binding.efLogo.alpha = 0f
        viewModel.getUser()
        binding.efLogo.animate().setDuration(500).alpha(0.75f)
        viewModel.navigateToHomeScreen.observe(this, Observer { navigate ->
            if(navigate != null){
                binding.efLogo.animate().setDuration(1).alpha(1f).withEndAction {
                    intent = Intent(this, SignInActivity::class.java)
                    if(navigate == true){
                        intent = Intent(this, HomeScreen::class.java)
                    }
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    startActivity(intent)
                    finish()
                }
                viewModel.onUserGot()
            }
        })


    }
}