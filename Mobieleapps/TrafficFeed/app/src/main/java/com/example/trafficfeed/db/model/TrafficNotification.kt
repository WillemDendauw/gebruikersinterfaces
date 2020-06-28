package com.example.trafficfeed.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="notifications")
data class TrafficNotification(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: String,
    val source: String,
    val transport: String,
    val message: String,
    val latitude: Double,
    val longitude: Double,
    var date: String
)