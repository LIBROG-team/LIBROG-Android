package com.example.librog.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.librog.data.entities.Book

@Dao
interface BookDao {

    @Query("SELECT * FROM Book")
    fun getFlowerpotList(): List<Book>

}