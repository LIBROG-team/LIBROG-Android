package com.example.librog.data.remote.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserDataInterface {
    @GET("/records/statistics/{userIdx}")
    fun getUserStat(@Path("userIdx") userIdx: Int): Call<UserStatResponse>

    @GET("/contents/notice")
    fun getHomeNotice(): Call<HomeNoticeResponse>
}