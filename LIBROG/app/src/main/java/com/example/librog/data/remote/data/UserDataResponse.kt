package com.example.librog.data.remote.data

import com.google.gson.annotations.SerializedName

//마이페이지 유저 통계
data class UserStatResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: UserStatResult
)

data class UserStatResult(
    @SerializedName("userIdx") var userIdx: Int,
    @SerializedName("flowerCnt") var flowerCnt: Int,
    @SerializedName("readingCnt") var readingCnt: Int,
    @SerializedName("starRatingCnt") var starRatingCnt: Int,
    @SerializedName("quoteCnt") var quoteCnt: Int,
    @SerializedName("contentCnt") var contentCnt: Int
)


data class HomeNoticeResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<HomeNoticeResult>
)

data class HomeNoticeResult(
    @SerializedName("idx") val idx: Int,
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("connectUrl") val connectUrl: String,
    @SerializedName("noticeImgUrl") val noticeImgUrl: String,
)

























////홈 최근 읽은 책
//data class RecentReadResponse(
//    @SerializedName("isSuccess") val isSuccess: Boolean?,
//    @SerializedName("code") val code: Int?,
//    @SerializedName("message") val message: String?,
//    @SerializedName("result") val result: RecentReadResult
//)
//
//data class RecentReadResult(
//    @SerializedName("userIdx") var userIdx: Int,
//    @SerializedName("readingRecordIdx") var readingRecordIdx: Int,
//    @SerializedName("bookName") var bookName: String,
//    @SerializedName("author") var author: ArrayList<String>?,
//    @SerializedName("publishedDate") var publishedDate: String,
//    @SerializedName("bookImgUrl") var bookImgUrl: String,
//    @SerializedName("recordDate") var recordDate: String
//)

