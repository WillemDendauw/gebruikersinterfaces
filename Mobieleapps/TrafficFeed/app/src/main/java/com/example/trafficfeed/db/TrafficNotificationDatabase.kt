package com.example.trafficfeed.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.trafficfeed.db.model.TrafficNotification
import com.example.trafficfeed.db.model.TrafficNotificationDAO
import com.example.trafficfeed.util.InitializeDatabase

@Database(
    entities = arrayOf(TrafficNotification::class),
    version = 1
)

abstract class TrafficNotificationDatabase : RoomDatabase() {

    abstract fun trafficDao() : TrafficNotificationDAO

    companion object {
        @Volatile
        private var instance: TrafficNotificationDatabase? = null

        fun getInstance(context: Context): TrafficNotificationDatabase {
            return instance ?: synchronized(this){
                instance ?: return Room.databaseBuilder(
                    context.applicationContext,
                    TrafficNotificationDatabase::class.java,
                    "traffic_database"
                ).addCallback(object: Callback(){
                    // onCreate --> eenmalig bij aanmaken DB, hier is onCreate juister
                    // onOpen --> telkens bij het openen van de DB
                    override fun onCreate(db: SupportSQLiteDatabase){
                        super.onCreate(db)
                        val uploadWorkRequest = OneTimeWorkRequestBuilder<InitializeDatabase>().build()
                        WorkManager.getInstance(context).enqueue(uploadWorkRequest)
                    }
                }).build().also{instance=it}
            }

            // als je start met de 'lange' versie, kan je de .addCallback() ook netjes afwerken
            // instance een referentie naar het database-object
            // .addCallback() zorgt ervoor dat na het opstarten 'iets' wordt uitgevoerd op de databank
            // 'iets' is hier het opvullen van de databank
            /*
            val tempInstance: TrafficNotificationDatabase? = instance
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val tempInstance = instance
                if (tempInstance == null) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TrafficNotificationDatabase::class.java,
                        "traffic_Database"
                    ).addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val uploadWorkRequest = OneTimeWorkRequestBuilder<InitializeDatabase>().build()
                            WorkManager.getInstance(context).enqueue(uploadWorkRequest)
                        }
                    }).build()
                    this.instance = instance
                    return instance
                } else return tempInstance
            }
             */
        }
    }
}