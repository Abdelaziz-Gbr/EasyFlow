package com.easyflow.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.easyflow.database.models.TripDatabaseModel

@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg trip: TripDatabaseModel)

    @Query("SELECT * FROM trips")
    fun getALLTrips(): LiveData<List<TripDatabaseModel>>

    @Query("UPDATE trips SET used=true WHERE id=:tripID")
    fun markTripUsed(tripID: String)

}