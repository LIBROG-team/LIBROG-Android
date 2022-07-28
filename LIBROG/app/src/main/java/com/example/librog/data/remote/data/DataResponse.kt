package com.example.librog.data.remote.data

import com.google.gson.annotations.SerializedName

data class DataResponse1(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<FpResult>
)

data class DataResponse2(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<UnlockedFpResult>
)

data class DataResponse3(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<LockedFpResult>
)

data class FpResult(
    @SerializedName("flowerDataIdx") var flowerDataIdx: Int,
    @SerializedName("flowerPotIdx") var flowerPotIdx: Int,
    @SerializedName("name") var name: String,
    @SerializedName("engName") var engName: String,
    @SerializedName("flowerImgUrl") var flowerImgUrl: String,
    @SerializedName("maxExp") var maxExp: Int,
    @SerializedName("bloomingPeriod") var bloomingPeriod: String,
    @SerializedName("content") var content: String,
    @SerializedName("type") var type: String,
    @SerializedName("startDate") var startDate: String,
    @SerializedName("lastDate") var lastDate: String,
    @SerializedName("exp") var exp: Int,
    @SerializedName("recordCount") var recordCount: Int
)

data class UnlockedFpResult(
    @SerializedName("flowerDataIdx") var idx: Int,
    @SerializedName("name") var name: String,
    @SerializedName("type") var type: String,
    @SerializedName("bloomingPeriod") var bloomingPeriod: String,
    @SerializedName("flowerImgUrl") var flowerImgUrl: String
)

data class LockedFpResult(
    @SerializedName("flowerDataIdx") var idx: Int,
    @SerializedName("name") var name: String,
    @SerializedName("type") var type: String,
    @SerializedName("condition") var condition: String,
    @SerializedName("flowerImgUrl") var flowerImgUrl: String
)
