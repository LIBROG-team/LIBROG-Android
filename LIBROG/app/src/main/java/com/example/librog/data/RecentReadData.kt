package com.example.librog.data

import androidx.room.Entity

data class RecentReadData(
    var coverImg: Int,
    var title : String,
    var writer : String,
    var date: String
)
