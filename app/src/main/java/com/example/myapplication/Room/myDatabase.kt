package com.example.myapplication.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.model.imagesItem

@Database(entities = [imagesItem::class], version = 12, exportSchema = false)
abstract class myDatabase: RoomDatabase() {

    abstract val dao: Dao

    companion object {

        @Volatile
        private var INSTANCE: myDatabase? = null

        fun getDatabase(context: Context): myDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        myDatabase::class.java,
                        "my_gallery"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}