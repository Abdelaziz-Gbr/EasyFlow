package com.easyflow.firebaseMessagingService

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.easyflow.R
import com.easyflow.activities.splashScreen.SplashScreen
import com.easyflow.utils.Constants.channelID
import com.easyflow.utils.Constants.channelName
import com.easyflow.utils.subscribeToMainFeed
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    init {
        subscribeToMainFeed()
    }
    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.apply {
            val recievedTag = tag?: "0"
            val myTag =
                try {
                recievedTag.toInt()
            } catch (e: java.lang.NumberFormatException){
                0
            }
            val myTitle = title?: "Easy Flow"
            val myBody = body?: "check the app for updates"
            generateNotification(myTag, myTitle, myBody)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FireBase_Token", token)
    }

    private fun generateNotification(tag: Int, title: String, message: String){

        val intent = Intent(this, SplashScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(R.drawable.easyflow_logo)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //checking if the user api level is equal to or higher than Oreo API so the firebase System is supported.

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(tag , builder.build())
    }

}