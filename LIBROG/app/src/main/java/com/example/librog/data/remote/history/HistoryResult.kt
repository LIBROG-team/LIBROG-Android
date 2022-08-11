package com.example.librog.data.remote.history

import com.google.gson.annotations.SerializedName


// 2.9, 2.10, 2.11
data class HistoryResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<FilteredHistoryResult>
)

data class HistoryResult(
    @SerializedName("userIdx") var userIdx: Int?,
    @SerializedName("readingRecordIdx") var readingRecordIdx: Int?,
    @SerializedName("bookName") var bookName: String?,
    @SerializedName("author") var author: ArrayList<String>?,
    @SerializedName("publishedDate") var publishedDate: String?,
    @SerializedName("bookImgUrl") var bookImgUrl: String?,
    @SerializedName("recordedDate") var recordedDate: String?
)


data class FilteredHistoryResult(
    @SerializedName("readingRecordIdx") var readingRecordIdx: Int?,
    @SerializedName("bookIdx") var bookIdx: Int?,
    @SerializedName("flowerPotIdx") var flowerPotIdx: Int?,
    @SerializedName("date") var date: String?,
    @SerializedName("starRating") var starRating: Int?,
    @SerializedName("status") var status: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("bookImgUrl") var bookImgUrl: String?
)


// 2.5  POST 통신 시 받는 데이터
data class AddBookResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: CreatedRecordId
)


// 2.5 POST 통신 시 생성된 기록의 id
data class CreatedRecordId(
    @SerializedName("createdRecordId") var createdRecordId: Int?
)


// 2.5 독서기록 추가 시 유저가 입력하는 정보 (POST body)
data class UserBookRecord(
    @SerializedName("bookName") var bookName: String?,
    @SerializedName("authorArr") var authorArr: ArrayList<String>?,
    @SerializedName("publisher") var publisher: String?,
    @SerializedName("publishedDate") var publishedDate: String?,
    @SerializedName("bookInstruction") var bookInstruction: String?,
    @SerializedName("bookImgUrl") var bookImgUrl: String?,
    @SerializedName("userIdx") var userIdx: Int?,
    @SerializedName("starRating") var starRating: Int?,
    @SerializedName("quote") var quote: String?,
    @SerializedName("content") var content: String?,
)
