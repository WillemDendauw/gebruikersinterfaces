package com.example.trafficfeed.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trafficfeed.db.model.TrafficNotification

@Dao
interface TrafficNotificationDAO {
    @Query("Select * From traffic_notifications")
    fun getAll(): LiveData<List<TrafficNotification>>

    @Query("Select * From traffic_notifications where statement like :criteria")
    fun findByCriteria(criteria: String): List<TrafficNotification>

    @Insert
    fun insertAll(vararg trafficNotification: TrafficNotification)

    @Delete
    fun delete(trafficNotification: TrafficNotification)
}