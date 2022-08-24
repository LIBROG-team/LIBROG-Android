package com.example.librog.data.remote

import com.example.librog.data.remote.data.HomeNoticeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeRetrofitInterface {
    @GET("/contents/recommendBooks")
    fun getRecommend(): Call<RecommendResponse>

    @GET("records/bookRecords/{userIdx}")
    fun getRecentBook(@Path("userIdx") userIdx: Int): Call<RecentReadResponse>

    @GET("flowerpots/flowerpotMain/{userIdx}")
    fun getMainPot(@Path("userIdx") userIdx: Int): Call<MainPotResponse>

    @GET("/records/mainpage/{userIdx}")
    fun getDailyStatus(@Path("userIdx") userIdx: Int): Call<MainDailyResponse>
}