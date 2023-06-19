package com.easyflow.activities.homeScreen.fragmentSettings

import android.widget.CompoundButton
import androidx.lifecycle.ViewModel
import com.easyflow.database.TicketDao
import com.easyflow.database.UserDao
import com.easyflow.utils.*

class SettingsViewModel : ViewModel() {

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

    fun logout(userDataSource: UserDao, ticketDataSource : TicketDao){
        logUserOut(userDataSource, ticketDataSource)
    }

}