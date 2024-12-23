package com.easyflow.database

import androidx.room.*
import com.easyflow.database.models.TripDatabaseModel

@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg trip: TripDatabaseModel)

    @Query("SELECT * FROM trips")
    suspend fun getAllTrips(): List<TripDatabaseModel>

    @Query("UPDATE trips SET used=true WHERE id=:tripID")
    suspend fun markTripUsed(tripID: String)

    //@Delete(TripDatabaseModel::class)
    @Query("DELETE FROM trips")
    suspend fun deleteAllTrips()

    @Query("SELECT * FROM trips LIMIT 1")
    suspend fun getFirstTrip(): TripDatabaseModel?

    @Query("SELECT * FROM trips LIMIT 1 OFFSET 1")
    suspend fun getSecondTrip(): TripDatabaseModel?

    @Query("SELECT * FROM trips LIMIT 1 OFFSET 2")
    suspend fun getThirdTrip(): TripDatabaseModel?

    @Query("SELECT * FROM trips LIMIT 1 OFFSET 3")
    suspend fun getFourthTrip(): TripDatabaseModel?

    @Query("SELECT * FROM trips LIMIT 1 OFFSET 4")
    suspend fun getFifthTrip(): TripDatabaseModel?

}