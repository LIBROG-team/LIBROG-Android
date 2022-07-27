package com.example.librog.data.remote.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DataInterface {
    @GET("/flowerpots/{userIdx}")
    fun getFpList(@Path("userIdx") userIdx: Int): Call<DataResponse1>

    @GET("/flowerpots/{userIdx}/userflowerlist")
    fun getUnlockedFpResult(@Path("userIdx") userIdx: Int): Call<DataResponse2>


    @GET("/flowerpots/{userIdx}/unacqUserflowerlist")
    fun getLockedFpResult(@Path("userIdx") userIdx: Int): Call<DataResponse3>

}