package com.easyflow.utils

import android.util.Log
import com.easyflow.cache.UserCache
import com.easyflow.cache.UserKey
import com.easyflow.database.TicketDao
import com.easyflow.database.UserDao
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.runBlocking

//todo sign in routine?

fun subscribeToMainFeed(){
    FirebaseMessaging.getInstance().subscribeToTopic("main_topic")
        .addOnCompleteListener{
                task ->
            if(task.isSuccessful){
                Log.d("Firebase_subscription_main", "succeeded")
            }
            else{
                Log.d("Firebase_subscription_main", "failed")
            }
        }
}

fun unSubscribeToMainFeed(){
    FirebaseMessaging.getInstance().unsubscribeFromTopic("main_topic")
        .addOnCompleteListener{
                task ->
            if(task.isSuccessful){
                Log.d("Firebase_unsubscription_main", "succeeded")
            }
            else{
                Log.d("Firebase_unsubscription_main", "failed")
            }
        }
}

fun subscribeToUserFeed(){
    FirebaseMessaging.getInstance().subscribeToTopic("${UserCache.username}")
        .addOnCompleteListener{
                task ->
            if(task.isSuccessful){
                Log.d("Firebase_subscription_user", "succeeded")
            }
            else{
                Log.d("Firebase_subscription_user", "failed")
            }
        }
}

fun unSubscribeToUserFeed(){
    FirebaseMessaging.getInstance().unsubscribeFromTopic("${UserCache.username}")
        .addOnCompleteListener{
                task ->
            if(task.isSuccessful){
                Log.d("Firebase_unsubscription_user", "succeeded")
            }
            else{
                Log.d("Firebase_unsubscription_user", "failed")
            }
        }
}

fun logUserOut(userDataSource: UserDao, ticketDataSource : TicketDao){
    runBlocking { dropAllData(userDataSource, ticketDataSource) }
    clearCaches()
}

fun clearCaches() {
    UserCache.freeAll()
    UserKey.value = null
}

suspend fun dropAllData(userDataSource: UserDao, ticketDataSource : TicketDao){
    userDataSource.removeUser()
    ticketDataSource.deleteAllTickets()
}

