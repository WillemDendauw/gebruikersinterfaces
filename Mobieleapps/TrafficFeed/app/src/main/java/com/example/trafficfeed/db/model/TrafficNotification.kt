package com.example.trafficfeed.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "traffic_notifications")
data class TrafficNotification(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "statement")
    val name: String,
    val type: String,
    val source: String,
    val transport: String,
    val message: String,
    val latitude: Double,
    val longitude: Double,
    var date: String
)