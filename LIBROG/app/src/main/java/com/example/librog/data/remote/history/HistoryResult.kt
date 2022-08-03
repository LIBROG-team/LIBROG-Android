package com.example.librog.data.remote.history

import com.google.gson.annotations.SerializedName


data class HistoryResult(
    @SerializedName("userIdx") var userIdx: Int?,
    @SerializedName("readingRecordIdx") var readingRecordIdx: Int?,
    @SerializedName("bookName") var bookName: String?,
    @SerializedName("author") var author: ArrayList<String>?,
    @SerializedName("publishedDate") var publishedDate: String?,
    @SerializedName("bookImgUrl") var bookImgUrl: String?,
    @SerializedName("recordedDate") var recordedDate: String?
)

