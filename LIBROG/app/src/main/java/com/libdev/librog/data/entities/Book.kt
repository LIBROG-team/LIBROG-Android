package com.libdev.librog.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey(autoGenerate = true) var idx: Int = 0,
    val name: String,
    val author: String,
    val publisher: String,
    val status: String,
    val publishedDate: String
)