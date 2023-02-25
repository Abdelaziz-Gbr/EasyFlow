package com.easyflow.repository

import com.easyflow.database.UserDao
import com.easyflow.models.User

class UserDatabaseRepository(private val userDao: UserDao) {
    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun getUserKey(): String?{
        return userDao.getUserKey()
    }

    suspend fun removeUser(){
        return userDao.removeUser()
    }

}