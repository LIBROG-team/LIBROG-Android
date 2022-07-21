package com.example.librog.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["idx"],
            childColumns = ["userIdx"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = FlowerData::class,
            parentColumns = ["idx"],
            childColumns = ["flowerDataIdx"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Flowerpot(
    @PrimaryKey(autoGenerate = true) var idx: Int = 0,
    var userIdx: Int,
    var flowerDataIdx: Int,
    var startDate: String,
    var lastDate: String,
    var exp: Int,
    var recordCount: Int,
    var status: String
)


