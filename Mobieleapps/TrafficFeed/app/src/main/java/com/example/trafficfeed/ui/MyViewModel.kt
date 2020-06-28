package com.example.trafficfeed.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.trafficfeed.db.DummyTrafficNotification
import com.example.trafficfeed.db.model.TrafficNotification
import com.example.trafficfeed.db.model.TrafficNotificationRepository
import kotlin.random.Random

class MyViewModel(application: Application) : AndroidViewModel(application) {
    val tag = "MyViewModel"
    var indexCurrentNotification = 0

    // private val notifications = TrafficNotificationRepository(application).notifications
    private val notifications : LiveData<List<TrafficNotification>>

    val notificationCount : LiveData<Int>
    //blijf beginnen met dummy voor wanneer viewmodel al bestaat, maar de notifications nog niet
    val selectedNotif = MutableLiveData(DummyTrafficNotification.get())

    init {
        // dit zou je ook al meteen bij declaratie kunnen doen
        // zie de code hierboven in commentaar
        val repo = TrafficNotificationRepository(application)
        notifications = repo.notifications

        Log.e(tag, "notifications.value: "+notifications.value)
        Log.e(tag, "notifications.size: "+notifications.value?.size ?: "onbestaand")

        notificationCount = Transformations.map(notifications){
            if(notifications.value?.size ?: 0 > 0) {
                // 'it' verwijst hier naar de value van de parameter 'notifications':
                // het type van notification is LiveData<List<TrafficNotification>>
                // het type van 'it' is List<TrafficNotification>
                selectedNotif.value = it[indexCurrentNotification]
            }
            notifications.value?.size ?: 0
        }
    }

    fun next(){
        if(notificationCount.value?: 0 > 0){
            indexCurrentNotification++
            indexCurrentNotification %= notificationCount.value ?: 0
            selectedNotif.value = notifications.value?.get(indexCurrentNotification)
        }
    }

    fun previous() {
        if (notificationCount.value ?: 0 > 0) {
            if (indexCurrentNotification == 0)
                indexCurrentNotification = (notificationCount.value ?: 1) - 1
            else
                indexCurrentNotification--

            indexCurrentNotification %= notificationCount.value ?: 0
            selectedNotif.value = notifications.value?.get(indexCurrentNotification)
            // opletten met negatieve getallen en modulo rekenen!
            // vermijd de bewerking a % b als a negatief is
            // (het hangt af van programmeertaal of je een negatief getal krijgt,
            // dan wel een postief. Wiskundigen en informatici zijn het hier niet eens
            // over de definite die gebruikt moet worden)
        }
    }

    fun chooseRandom() {
        var index = Random.nextInt(notificationCount.value?:0)
        // currentNotification = index
        selectedNotif.value = notifications.value?.get(index)
    }
}