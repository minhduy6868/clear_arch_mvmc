package com.example.clean_arch_demo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Maytinh::class], version = 1, exportSchema = false)
abstract class MaytinhDatabase : RoomDatabase() {   // Taạo Rooomdataabe
    abstract fun maytinhDao(): MaytinhDao

    companion object {
        @Volatile
        private var INSTANCE: MaytinhDatabase? = null

        fun getInstance(context: Context):MaytinhDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MaytinhDatabase::class.java,
                    "maytinh_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
