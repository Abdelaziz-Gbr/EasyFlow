package com.easyflow.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.easyflow.cache.UserCache
import com.easyflow.database.UserDatabase
import com.easyflow.database.models.UserDatabaseModel
import com.easyflow.repository.UserDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDatabaseViewModel(application: Application): AndroidViewModel(application) {/*
    private val databaseRepository: UserDatabaseRepository

    var userFromDB : MutableLiveData<UserDatabaseModel> = MutableLiveData()
    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        databaseRepository = UserDatabaseRepository(userDao)
    }
    fun addUser(user: UserDatabaseModel){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.addUser(user)
        }
    }

    fun removeUser(){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.removeUser()
        }
    }

    fun getUser(){
        viewModelScope.launch(Dispatchers.IO) {
           UserCache.cacheUser(databaseRepository.getUser())
        }
    }*/
}