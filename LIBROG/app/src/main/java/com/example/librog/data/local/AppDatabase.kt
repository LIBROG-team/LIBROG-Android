package com.example.librog.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.librog.data.entities.*

@Database(entities = [User::class, Book::class, FlowerData::class, Flowerpot::class, ReadingRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {


}