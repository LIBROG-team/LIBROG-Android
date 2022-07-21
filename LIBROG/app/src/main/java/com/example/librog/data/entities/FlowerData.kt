package com.example.librog.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FlowerData(
    @PrimaryKey(autoGenerate = true) var idx: Int = 0,
    var name: String,
    var engName: String,
    var flowerImgUrl: String,
    var flowerpotImgUrl: String,
    var maxExp: Int,
    var blooming: String,
    var content: String,
    var type: String,
    var status: String,
)
