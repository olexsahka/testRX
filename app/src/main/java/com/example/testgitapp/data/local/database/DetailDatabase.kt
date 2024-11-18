package com.example.testgitapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testgitapp.data.local.dao.CacheDetailDao
import com.example.testgitapp.data.local.entity.DetailEntity

@Database(entities = [DetailEntity::class], version = 1, exportSchema = false)
abstract class DetailDatabase : RoomDatabase() {

    abstract fun detailDao(): CacheDetailDao

    companion object {
        @Volatile
        private var instance: DetailDatabase? = null

        fun getInstance(context: Context): DetailDatabase {
            return instance ?: synchronized(this) {
                val localInstance = Room.databaseBuilder(
                    context.applicationContext,
                    DetailDatabase::class.java,
                    "cached_database"
                ).build()
                instance = localInstance
                localInstance
            }

        }
    }
}