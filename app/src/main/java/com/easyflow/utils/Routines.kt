package com.easyflow.utils

import android.util.Log
import com.easyflow.cache.UserCache
import com.easyflow.cache.UserKey
import com.easyflow.database.TicketDao
import com.easyflow.database.TripDao
import com.easyflow.database.UserDao
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.runBlocking
import com.easyflow.cache.sharedPreferences
import com.easyflow.network.Network
import com.easyflow.network.models.UserNetworkModel
import com.easyflow.network.models.toDatabaseMode


suspend fun signUserIn(user: UserNetworkModel, userDao: UserDao, ticketDao: TicketDao, fromSplashScreen: Boolean = false): Int{
    try {
        val signIn = Network.easyFlowServices.signIn(user)
        if(signIn.isSuccessful){
            val key = signIn.headers()["Authorization"]
            val userInfoRequest = Network.easyFlowServices.getUserInfo(key!!)
            if(userInfoRequest.isSuccessful){
                val userInfo = userInfoRequest.body()
                UserCache.cacheUser(userInfo)
                UserKey.value = key
                if(sharedPreferences.data.getBoolean("sub_user", true)) subscribeToUserFeed()
                if (!fromSplashScreen) {
                    userDao.removeUser()
                    ticketDao.deleteAllTickets()
                    userDao.addUser(user.toDatabaseMode())
                }
                return 1
            }
        }
        else if (signIn.code() ==  502){
            return 2
        }
        return 0
    }
    catch (e: Exception){
        return 2
    }
}

fun subscribeToMainFeed(){
    FirebaseMessaging.getInstance().subscribeToTopic("main_topic")
        .addOnCompleteListener{
                task ->
            if(task.isSuccessful){
                Log.d("Firebase_subscription_main", "succeeded")
                with(sharedPreferences.data.edit()){
                    putBoolean("sub_main", true)
                    apply()
                }
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
                with(sharedPreferences.data.edit()){
                    putBoolean("sub_main", false)
                    apply()
                }
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
                with(sharedPreferences.data.edit()){
                    putBoolean("sub_user", true)
                    apply()
                }
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
                with(sharedPreferences.data.edit()){
                    putBoolean("sub_user", false)
                    apply()
                }
            }
            else{
                Log.d("Firebase_unsubscription_user", "failed")
            }
        }
}

fun logUserOut(userDataSource: UserDao, ticketDataSource : TicketDao, tripDao: TripDao){
    runBlocking { dropAllData(userDataSource, ticketDataSource, tripDao) }
    clearCaches()
    sharedPreferences.data.edit().clear().apply()
}

fun clearCaches() {
    UserCache.freeAll()
    UserKey.value = null
}

suspend fun dropAllData(userDataSource: UserDao, ticketDataSource : TicketDao, tripDao: TripDao){
    userDataSource.removeUser()
    ticketDataSource.deleteAllTickets()
    tripDao.deleteAllTrips()
}
/*

fun isWifiConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
}

fun isDataConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
}


*/
