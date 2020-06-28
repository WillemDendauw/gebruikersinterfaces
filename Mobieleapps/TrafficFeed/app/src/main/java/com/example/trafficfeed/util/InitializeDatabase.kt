package com.example.trafficfeed.util

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.trafficfeed.db.TrafficNotificationDatabase
import com.example.trafficfeed.db.model.TrafficNotification
import org.json.JSONObject

class InitializeDatabase(val appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val json:JSONObject = JsonUtils.loadJSONFromAsset(appContext, "verkeersmeldingen.json")
        val list: MutableList<TrafficNotification> = JsonUtils.parseJSON(json)

        list.forEach{
            not -> TrafficNotificationDatabase.getInstance(appContext).trafficDao().insertAll(not)
        }

        return Result.success()
    }
}