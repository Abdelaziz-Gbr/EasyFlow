package com.easyflow.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.easyflow.cache.UserKey
import com.easyflow.database.UserDatabase
import com.easyflow.models.User
import com.easyflow.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository
    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }
    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }
    fun cacheUserKey(){
        viewModelScope.launch(Dispatchers.IO) {
            UserKey.key = repository.getUserKey()
        }
    }

    fun removeUser(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeUser()
        }
    }
}