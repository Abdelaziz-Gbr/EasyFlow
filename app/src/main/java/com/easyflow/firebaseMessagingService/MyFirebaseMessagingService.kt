package com.easyflow.firebaseMessagingService

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.easyflow.R
import com.easyflow.activities.splashScreen.SplashScreen
import com.easyflow.utils.Constants.channelID
import com.easyflow.utils.Constants.channelName
import com.easyflow.utils.subscribeToMainFeed
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
class MyFirebaseMessagingService : FirebaseMessagingService() {

    init {
        subscribeToMainFeed()
    }
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("firebase", "message Received")
        if(message.notification != null){
            generateNotification(message.notification!!.title!!, message.notification!!.body!!)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FireBase_Token", token)
    }

    private fun generateNotification(title: String, message: String){

        val intent = Intent(this, SplashScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(R.drawable.easyflow_logo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
        
        builder = builder.setContent(getRemoteViews(title, message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //checking if the user api level is equal to or higher than Oreo API so the firebase System is supported.

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())
    }

    private fun getRemoteViews(title: String, message: String): RemoteViews? {
        val remoteView = RemoteViews("com.easyflow.firebaseMessagingService", R.layout.notification)
        remoteView.setTextViewText(R.id.notification_title, title)
        remoteView.setTextViewText(R.id.notification_message, message)
        remoteView.setImageViewResource(R.id.notification_image, R.drawable.easyflow_colored_logo)

        return remoteView
    }


}