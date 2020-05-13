package com.example.trafficfeed.db

import com.example.trafficfeed.db.model.TrafficNotification

object DummyTrafficNotification {
    fun get(): TrafficNotification {
        return TrafficNotification(
            "id_0000", "name", "type", "source",
            "transport", "message", 0.0, 0.0, "date"
        );
    }
}