package com.example.penduduk_lanteng.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.penduduk_lanteng.DB.dao.PendudukDao
import com.example.penduduk_lanteng.DB.entity.Penduduk

@Database(entities = [Penduduk::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pendudukDao(): PendudukDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_db"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
    }
}