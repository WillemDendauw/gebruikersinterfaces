package com.example.trafficfeed.db.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TrafficNotificationDAO {

    @Insert
    fun insertAll(vararg q: TrafficNotification)
    // let op! dit is een interface, zet dus geen accolades na
    // de hoofding van deze methode

    @Delete
    fun deleteAll(vararg q: TrafficNotification)
    // idem

    @Query("Select * From notifications")
    fun getAll(): LiveData<List<TrafficNotification>>
    // idem
}