package com.example.librog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FlowerpotTable")
data class FlowerpotData(
    var name: String,
    var engName: String,
    var startDate: String,
    var lastDate: String,
    var flowerpotImgUrl: String,
    var mostReadGenre: String,
    var recordCount: Int,
    var exp: Int,
    var maxExp: Int,
    var blooming: String,
    var content: String,
    var status: String,
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
