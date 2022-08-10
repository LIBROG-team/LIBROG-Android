package com.example.librog.data.remote.history

import com.google.gson.annotations.SerializedName

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

