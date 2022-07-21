package com.example.librog.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.librog.data.entities.*

@Database(
    entities = [User::class, Book::class, FlowerData::class, Flowerpot::class, ReadingRecord::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun flowerpotDao(): FlowerpotDao
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app database"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}