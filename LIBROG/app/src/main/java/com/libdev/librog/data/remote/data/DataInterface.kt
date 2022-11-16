package com.libdev.librog.data.remote.data

import retrofit2.Call
import retrofit2.http.*

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

    @POST("/flowerpots/flowerpotAdd/{userFlowerListIdx}")
    fun addFlowerpot(@Path("userFlowerListIdx") userFlowerListIdx: Int): Call<AddFpResponse>

    @DELETE("/flowerpots/flowerpotDelete/{flowerpotIdx}")
    fun deleteFlowerpot(@Path ("flowerpotIdx") flowerpotIdx: Int): Call<DeleteFpResponse>

    @POST("/flowerpots/new/{userIdx}")
    fun checkUnlockedFp(@Path("userIdx") userIdx: Int): Call<CheckUnlockedFpResponse>
}