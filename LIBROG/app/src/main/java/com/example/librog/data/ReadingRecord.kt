package com.example.librog.data

import androidx.room.Entity

@Entity(tableName = "ReadingRecordTable")
data class ReadingRecord(
    var coverImg: Int? = null,
    val title : String = "",
    val writer : String = "",
    var date: String = ""

)
