package com.example.librog.data

import androidx.room.Entity

data class Readbook(
    var coverImg: Int? = null,
    val title : String = "",
    val writer : String = "",
    var date: String = ""

)
