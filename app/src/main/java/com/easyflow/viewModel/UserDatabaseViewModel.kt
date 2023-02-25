package com.easyflow.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.easyflow.cache.UserKey
import com.easyflow.database.UserDatabase
import com.easyflow.models.User
import com.easyflow.models.ServerResponse
import com.easyflow.repository.UserDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDatabaseViewModel(application: Application): AndroidViewModel(application) {
    private val databaseRepository: UserDatabaseRepository
    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        databaseRepository = UserDatabaseRepository(userDao)
    }
    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.addUser(user)
        }
    }
    fun cacheUserKey(){
        viewModelScope.launch(Dispatchers.IO) {
            UserKey.key = databaseRepository.getUserKey()
        }
    }

    fun removeUser(){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.removeUser()
        }
    }
}