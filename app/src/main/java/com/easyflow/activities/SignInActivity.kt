package com.easyflow.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.easyflow.BuildConfig
import com.easyflow.R
import com.easyflow.cache.UserKey
import com.easyflow.cache.LastResopnse
import com.easyflow.databinding.ActivitySignInBinding
import com.easyflow.models.User
import com.easyflow.network.NetworkManager
import com.easyflow.viewModel.UserViewModel

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        binding = DataBindingUtil.setContentView<ActivitySignInBinding>(this, R.layout.activity_sign_in)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        binding.signInButton.setOnClickListener {
            val userName = if (BuildConfig.DEBUG) "haridy" else binding.userEmail.text.toString()
            val userPassword = if (BuildConfig.DEBUG) "haridy" else binding.userPassword.text.toString()

            val network = NetworkManager()
            network.signIn(userName, userPassword, this)
            //todo maybe use viewmodel and progressBar to it
            //replace this with decent loading animation that waits at least 1500 ms
            binding.loading.animate().setDuration(1600).alpha(1f).withEndAction{
            if(UserKey.key == null){
                Toast.makeText(this, "error code : ${LastResopnse.code}", Toast.LENGTH_LONG).show()
                }
            else{
                if(binding.staySignedInCheck.isChecked)
                    userViewModel.addUser(User(userName = userName, password = userPassword, userKey = UserKey.key))
                Toast.makeText(this, "loggedIn Successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, HomeScreen::class.java)
                startActivity(intent)
                finish()
                }
            }


        }


    }
}