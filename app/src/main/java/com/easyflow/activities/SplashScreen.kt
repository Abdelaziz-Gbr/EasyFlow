package com.easyflow.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.easyflow.R
import com.easyflow.cache.UserKey
import com.easyflow.databinding.ActivitySplashScreenBinding
import com.easyflow.viewModel.UserViewModel

class SplashScreen : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val binding = DataBindingUtil.setContentView<ActivitySplashScreenBinding>(this, R.layout.activity_splash_screen)
        binding.efLogo.alpha = 0f
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        viewModel.cacheUserKey()
        binding.efLogo.animate().setDuration(1500).alpha(1f).withEndAction {
            val intent : Intent?
            //todo check if userKey Valid using test API from the server
            if(UserKey.key != null)
                intent = Intent(this, HomeScreen::class.java)
            else
                intent = Intent(this, SignInActivity::class.java)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(intent)
            finish()
        }
    }

}