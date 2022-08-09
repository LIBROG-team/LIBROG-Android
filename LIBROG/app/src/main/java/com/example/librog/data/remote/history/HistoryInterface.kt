package com.example.librog.data.remote.history

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HistoryInterface {
    @GET("/records/bookRecords/{userIdx}")
    fun getCurrentHistory(@Path("userIdx") userIdx: Int): Call<ArrayList<HistoryResult>>

}