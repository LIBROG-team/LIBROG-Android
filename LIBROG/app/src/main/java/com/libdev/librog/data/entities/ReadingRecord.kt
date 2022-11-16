package com.libdev.librog.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = ["idx"],
            childColumns = ["bookIdx"]
        ),
        ForeignKey(
            entity = Flowerpot::class,
            parentColumns = ["idx"],
            childColumns = ["flowerpotIdx"]
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["idx"],
            childColumns = ["userIdx"]
        )
    ]
)
data class ReadingRecord(
    @PrimaryKey(autoGenerate = true) var idx: Int = 0,
    var bookIdx: Int,
    var flowerpotIdx: Int,
    var userIdx: Int,
    var type: String,
    var date: String,
    var status: String,
    var starRating: Int,
    var content: String,
    var quote: String
)
