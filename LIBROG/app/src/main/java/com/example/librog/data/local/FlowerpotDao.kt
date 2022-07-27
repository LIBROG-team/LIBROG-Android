package com.example.librog.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.entities.Flowerpot

@Dao
interface FlowerpotDao {

    @Query("SELECT * FROM FlowerData")
    fun getFlowerpotList(): List<FlowerData>

    @Query("SELECT * FROM FlowerData WHERE idx = :idx")
    fun getFlowerpotByIdx(idx: Int): List<FlowerData>

    @Query("SELECT * FROM FlowerData JOIN Flowerpot ON FlowerData.idx = Flowerpot.flowerDataIdx AND FlowerData.idx = :idx")
    fun getFlowerDataAndFlowerpotByIdx(idx: Int): List<FlowerData>

    @Insert
    fun insertFlowerpot(flowerpot: Flowerpot)

    @Insert
    fun insertFlowerData(flowerData: FlowerData)


}
