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
        )
    ]
)
data class BookImgUrl(
    @PrimaryKey(autoGenerate = true) var idx: Int = 0,
    var bookIdx: Int,
    var imgUrl: String
)
