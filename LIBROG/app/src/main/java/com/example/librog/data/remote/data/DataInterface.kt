package com.example.librog.data.remote.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DataInterface {
    @GET("/flowerpots/{userIdx}")
    fun getFpList(@Path("userIdx") userIdx: Int): Call<DataResponse1>

    @GET("/flowerpots/{userIdx}/userflowerlist")
    fun getUnlockedFpResult(@Path("userIdx") userIdx: Int): Call<DataResponse2>


    @GET("/flowerpots/{userIdx}/unacqUserflowerlist")
    fun getLockedFpResult(@Path("userIdx") userIdx: Int): Call<DataResponse3>

    @GET("/flowerpots/{userIdx}/searchAcqFlower?flowerName=")
    fun searchUnlockedFpResult(@Path("userIdx") userIdx: Int, @Query("flowerName") name:String): Call<DataResponse2>

    @GET("/flowerpots/{userIdx}/searchUnacqFlower?flowerName=")
    fun searchLockedFpResult(@Path("userIdx") userIdx: Int, @Query("flowerName") name:String): Call<DataResponse3>

    @GET("/flowerpots/flowerPotInfo/{flowerDataIdx}")
    fun getDetailFp(@Path("flowerDataIdx") flowerDataIdx: Int): Call<DataResponse4>

}