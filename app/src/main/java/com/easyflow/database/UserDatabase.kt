package com.easyflow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.easyflow.database.models.TicketDatabaseModel
import com.easyflow.database.models.TripDatabaseModel
import com.easyflow.database.models.UserDatabaseModel

@Database(entities = [UserDatabaseModel::class, TicketDatabaseModel::class, TripDatabaseModel::class], version = 7, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun ticketDao(): TicketDao

    abstract fun tripDao(): TripDao
    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                    "user_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}