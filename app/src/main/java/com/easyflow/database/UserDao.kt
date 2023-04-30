package com.easyflow.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.easyflow.database.models.UserDatabaseModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserDatabaseModel)


    @Query("DELETE FROM user_table WHERE id = 0")
    suspend fun removeUser()

    @Query("SELECT * FROM user_table WHERE id = 0")
    suspend fun getUser(): UserDatabaseModel?
}