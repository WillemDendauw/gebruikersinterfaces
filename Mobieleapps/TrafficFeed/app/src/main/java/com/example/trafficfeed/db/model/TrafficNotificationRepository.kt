package com.example.trafficfeed.db.model

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.trafficfeed.db.TrafficNotificationDatabase

class TrafficNotificationRepository(application: Application) {
    val notifications : LiveData<List<TrafficNotification>>

    init {
        // oude code zonder 'also':
        //      val dao = TrafficNotifcationDatabase.getInstance(application).trafficDao()
        //      notifications = dao.getAll()
        // dit geeft problemen met threads die nog niet afgelopen zijn,
        // omdat je hierboven 'also' niet gebruikt

        TrafficNotificationDatabase.getInstance(application).trafficDao().also {
            notifications = it.getAll()
        }
    }
}