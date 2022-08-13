package com.example.librog.data.remote

import com.example.librog.data.remote.data.HomeNoticeResponse
import retrofit2.Call
import retrofit2.http.GET

interface HomeRetrofitInterface {
    @GET("/contents/recommendBooks")
    fun getRecommend(): Call<RecommendResponse>
}