package com.easyflow.activities.trips

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.easyflow.R
import com.easyflow.activities.signIn.signActivity.SignInActivity

class TripsActivity : AppCompatActivity() {
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
    }
}