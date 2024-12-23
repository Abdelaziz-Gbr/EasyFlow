package com.easyflow.appScreens.settings

import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easyflow.cache.SharedPreferences
import com.easyflow.database.TicketDao
import com.easyflow.database.TripDao
import com.easyflow.database.UserDao
import com.easyflow.utils.*

class SettingsViewModel : ViewModel() {


    var subMain = MutableLiveData<Boolean>(false)
    var subUser = MutableLiveData<Boolean>(false)

    init {
        subMain.value = SharedPreferences.data.getBoolean("sub_main", true)
        subUser.value = SharedPreferences.data.getBoolean("sub_user", true)
    }
    fun onSwitchMainCheckedChanged(switch: CompoundButton, checked: Boolean) {
        if (checked) {
            subscribeToMainFeed()
        } else {
            unSubscribeToMainFeed()
        }
    }


    fun onSwitchUserCheckedChanged(switch: CompoundButton, checked: Boolean) {
        if (checked) {
            subscribeToUserFeed()
        } else {
            unSubscribeToUserFeed()
        }
    }

    fun logout(userDataSource: UserDao, ticketDataSource : TicketDao, tripDataSource : TripDao){
        logUserOut(userDataSource, ticketDataSource, tripDataSource)
    }

}