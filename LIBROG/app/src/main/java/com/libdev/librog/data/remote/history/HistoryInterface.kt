package com.libdev.librog.data.remote.history

import retrofit2.Call
import retrofit2.http.*

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

    @POST("/records/addition")
    fun addUserBookRecord(@Body userBookRecord: UserBookRecord): Call<AddBookResponse>

    @GET("/records/{readingRecordIdx}")
    fun getDetailReadingRecordByIdx(@Path("readingRecordIdx") readingRecordIdx: Int): Call<DetailReadingRecordResponse>

    @PATCH("/records/fix")
    fun fixReadingRecord(@Body fixBookRecordData: FixBookRecordData): Call<FixResponse>

    @HTTP(method = "DELETE", path ="/records/removal", hasBody = true)
    fun deleteReadingRecord(@Body recordsIdx: DeleteBookRecordData): Call<DeleteRecordResponse>
}