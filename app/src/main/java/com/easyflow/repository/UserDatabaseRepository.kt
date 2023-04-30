package com.easyflow.repository

import com.easyflow.database.UserDao
import com.easyflow.database.models.UserDatabaseModel

class UserDatabaseRepository(private val userDao: UserDao) {
/*    suspend fun addUser(user: UserDatabaseModel){
        userDao.addUser(user)
    }

    suspend fun getUserKey(): String?{
        return userDao.getUserKey()
    }

    suspend fun removeUser(){
        return userDao.removeUser()
    }

    suspend fun getUser(): UserDatabaseModel?{
        return userDao.getUser()
    }*/

}