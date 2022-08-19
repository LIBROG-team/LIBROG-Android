package com.example.librog.data.remote.history

import com.google.gson.annotations.SerializedName


// 2.4 독서기록 수정 PATCH
// PATCH 통신 시 받는 데이터
data class FixResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: FixBookRecordResult
)

data class FixBookRecordResult(
    @SerializedName("fieldCount") var fieldCount: Int,
    @SerializedName("affectedRows") var affectedRows: Int,
    @SerializedName("insertId") var insertId: Int,
    @SerializedName("info") var info: Int,
    @SerializedName("serverStatus") var serverStatus: Int,
    @SerializedName("warningStatus") var warningStatus: Int,
    @SerializedName("changedRows") var changedRows: Int,
)

// PATCH 통신 시 추가하는 데이터
data class FixBookRecordData(
    @SerializedName("starRating") var starRating: Int,
    @SerializedName("quote") var quote: String?,
    @SerializedName("content") var content: String?,
    @SerializedName("idx") var readingRecordIdx: Int,
)


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
    @SerializedName("readingRecordIdx") var readingRecordIdx: Int,
    @SerializedName("bookIdx") var bookIdx: Int,
    @SerializedName("flowerPotIdx") var flowerPotIdx: Int,
    @SerializedName("date") var date: String?,
    @SerializedName("starRating") var starRating: Int?,
    @SerializedName("status") var status: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("bookImgUrl") var bookImgUrl: String?
)


// 2.3  POST 통신 시 받는 데이터
data class AddBookResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: CreatedRecordId
)


// 2.3 POST 통신 시 생성된 기록의 id
data class CreatedRecordId(
    @SerializedName("createdRecordId") var createdRecordId: Int?
)


// 2.3 독서기록 추가 시 유저가 입력하는 정보 (POST body)
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


// DELETE 통신
data class DeleteRecordResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: DeleteBookRecordResult
)

data class DeleteBookRecordResult(
    @SerializedName("fieldCount") var fieldCount: Int,
    @SerializedName("affectedRows") var affectedRows: Int,
    @SerializedName("insertId") var insertId: Int,
    @SerializedName("info") var info: Int,
    @SerializedName("serverStatus") var serverStatus: Int,
    @SerializedName("warningStatus") var warningStatus: Int,
)

data class DeleteBookRecordData(
    @SerializedName("recordsIdx") var readingRecordIdx: Int
)


// 2.8 독서기록 상세 정보
data class DetailReadingRecordResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: DetailReadingRecordResult
)

data class DetailReadingRecordResult(
    @SerializedName("readingRecordIdx") var readingRecordIdx: Int,
    @SerializedName("bookImgUrl") var bookImgUrl: String?,
    @SerializedName("name") var name: String,
    @SerializedName("author") var author: ArrayList<String>?,
    @SerializedName("bookInstruction") var bookInstruction: String?,
    @SerializedName("starRating") var starRating: Int,
    @SerializedName("quote") var quote: String?,
    @SerializedName("content") var content: String?
)