package com.example.librog.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.librog.data.FlowerpotData

@Dao
interface FlowerpotDao {

    @Query("SELECT * FROM FlowerpotTable")
    fun getFlowerpotList(): List<FlowerpotData>


}
