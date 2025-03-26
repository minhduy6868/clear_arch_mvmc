package com.example.clean_arch_demo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Hocphan::class], version = 1, exportSchema = false)
abstract class HocphanDatabase : RoomDatabase() {   // Taạo Rooomdataabe
    abstract fun hocphanDao(): HocphanDao

    companion object {
        @Volatile
        private var INSTANCE: HocphanDatabase? = null

        fun getInstance(context: Context): HocphanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HocphanDatabase::class.java,
                    "hocphan_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
