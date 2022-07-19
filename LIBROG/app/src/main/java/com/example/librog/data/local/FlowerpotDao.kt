package com.example.librog.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.librog.data.entities.FlowerData

@Dao
interface FlowerpotDao {

    @Query("SELECT * FROM FlowerpotTable")
    fun getFlowerpotList(): List<FlowerData>


}
