package com.easyflow.activities.trips

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.easyflow.R
import com.easyflow.activities.signIn.signActivity.SignInActivity

class TripsActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips)
        val gobackText = findViewById<TextView>(R.id.back_where_text)

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val offline = sharedPreferences.getBoolean("offline", true)
        gobackText.text = if(offline) "go back to sign in screen" else "go back to home screen"
        gobackText.setOnClickListener {
            if(offline){
                val intent = Intent(this, SignInActivity::class.java)
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