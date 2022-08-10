package com.example.librog.data.remote.history

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HistoryInterface {
    @GET("/records/bookRecords/{userIdx}")
    fun getRecentBookRecord(@Path("userIdx") userIdx: Int): Call<ArrayList<HistoryResult>>

    @GET("/records/readingRecord/filter/rating/{userIdx}")
    fun getHistoryFilteredByRate(@Path("userIdx") userIdx: Int): Call<HistoryResponse>

    @GET("/records/readingRecord/filter/recent/{userIdx}")
    fun getHistoryFilteredByRecent(@Path("userIdx") userIdx: Int): Call<HistoryResponse>

    @GET("/records/readingRecord/filter/title/{userIdx}")
    fun getHistoryFilteredByTitle(@Path("userIdx") userIdx: Int): Call<HistoryResponse>

    @GET("/records/flowerpot/{flowerPotIdx}")
    fun getFlowerpotBookRecord(@Path("flowerPotIdx") flowerpotIdx: Int): Call<HistoryResponse>

}