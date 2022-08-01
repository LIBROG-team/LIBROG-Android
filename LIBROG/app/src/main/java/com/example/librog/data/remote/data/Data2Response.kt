package com.example.librog.data.remote.data

import com.google.gson.annotations.SerializedName


data class UserStatResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: UserStat
)

data class UserStat(
    @SerializedName("userIdx") var userIdx: Int,
    @SerializedName("flowerCnt") var flowerCnt: Int,
    @SerializedName("readingCnt") var readingCnt: Int,
    @SerializedName("starRatingCnt") var starRatingCnt: Int,
    @SerializedName("quoteCnt") var quoteCnt: Int,
    @SerializedName("contentCnt") var contentCnt: Int
)