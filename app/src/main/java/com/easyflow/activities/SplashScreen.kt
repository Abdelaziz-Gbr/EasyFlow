package com.easyflow.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.easyflow.R
import com.easyflow.cache.UserCache
import com.easyflow.databinding.ActivitySplashScreenBinding
import com.easyflow.models.User
import com.easyflow.repository.NetworkRepository
import com.easyflow.viewModel.NetworkViewModel
import com.easyflow.viewModel.UserDatabaseViewModel
import com.easyflow.viewModelFactory.NetworkViewModelFactory

class SplashScreen : AppCompatActivity() {
    private lateinit var databaseViewModel: UserDatabaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        //initialize the view
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val binding = DataBindingUtil.setContentView<ActivitySplashScreenBinding>(
            this,
            R.layout.activity_splash_screen
        )
        binding.efLogo.alpha = 0f

        //initialize network view model
        val networkRepository = NetworkRepository()
        val networkViewModelFactory = NetworkViewModelFactory(networkRepository)
        val networkViewModel =
            ViewModelProvider(this, networkViewModelFactory)[NetworkViewModel::class.java]

        //init database view model
        databaseViewModel = ViewModelProvider(this)[UserDatabaseViewModel::class.java]

        //getting the user from the db
        databaseViewModel.getUser()

        var intent = Intent(this, SignInActivity::class.java)

        binding.efLogo.animate().setDuration(1500).alpha(1f).withEndAction {
            if(UserCache.username != null && UserCache.password != null){
                networkViewModel.signIn(User(id = null, username = UserCache.username, password = UserCache.password))
                networkViewModel.loginResponse.observe(this) {
                        signResponse ->
                    if (signResponse) {
                        intent = Intent(this, HomeScreen::class.java)
                    }
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    startActivity(intent)
                    finish()
                }
            }
            //one of the previous calls has failed.
            else{
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                startActivity(intent)
                finish()
            }

        }


    }
}