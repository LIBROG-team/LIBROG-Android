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

data class DataResponse4(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<DetailFpResult>

)

data class FpResult(
    @SerializedName("flowerDataIdx") var flowerDataIdx: Int?,
    @SerializedName("flowerPotIdx") var flowerPotIdx: Int?,
    @SerializedName("name") var name: String?,
    @SerializedName("engName") var engName: String?,
    @SerializedName("flowerImgUrl") var flowerImgUrl: String?,
    @SerializedName("maxExp") var maxExp: Int?,
    @SerializedName("bloomingPeriod") var bloomingPeriod: String?,
    @SerializedName("content") var content: String?,
    @SerializedName("type") var type: String?,
    @SerializedName("startDate") var startDate: String?,
    @SerializedName("lastDate") var lastDate: String?,
    @SerializedName("exp") var exp: Int?,
    @SerializedName("recordCount") var recordCount: Int?
)

data class UnlockedFpResult(
    @SerializedName("flowerDataIdx") var idx: Int?,
    @SerializedName("name") var name: String?,
    @SerializedName("type") var type: String?,
    @SerializedName("bloomingPeriod") var bloomingPeriod: String?,
    @SerializedName("flowerImgUrl") var flowerImgUrl: String?,
    @SerializedName("userFlowerListIdx") var userFlowerListIdx: Int
)

data class LockedFpResult(
    @SerializedName("flowerDataIdx") var idx: Int,
    @SerializedName("name") var name: String,
    @SerializedName("type") var type: String,
    @SerializedName("condition") var condition: String,
    @SerializedName("flowerImgUrl") var flowerImgUrl: String
)


data class DetailFpResult(
    @SerializedName("flowerDataIdx") var idx: Int,
    @SerializedName("name") var name: String,
    @SerializedName("engName") var engName: String?,
    @SerializedName("content") var content: String?,
    @SerializedName("flowerImgUrl") var flowerImgUrl: String?

)


// POST 통신에서 사용되는 기본 데이터 클래스
data class AddFpResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: AddFpResult
)

data class AddFpResult(
    @SerializedName("fieldCount") var fieldCount: Int,
    @SerializedName("affectedRows") var affectedRows: Int,
    @SerializedName("insertId") var insertId: Int,
    @SerializedName("info") var info: String,
    @SerializedName("serverStatus") var serverStatus: Int,
    @SerializedName("warningStatus") var warningStatus: Int,
)

data class DeleteFpResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: DeleteFpResult
)

data class DeleteFpResult(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)