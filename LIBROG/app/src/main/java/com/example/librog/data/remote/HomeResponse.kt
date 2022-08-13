package com.example.librog.data.remote

import com.example.librog.data.remote.data.auth.SignUpResult
import com.google.gson.annotations.SerializedName

data class RecommendResponse(
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "result")val result: ArrayList<RecommendResult>,
)

data class RecommendResult(
    @SerializedName(value = "idx") val idx: Int,
    @SerializedName(value = "title") val title: String,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "author") val author: String,
    @SerializedName(value = "publisher") val publisher: String,
    @SerializedName(value = "bookCoverImg") val bookCoverImg: String,
    @SerializedName(value = "connectUrl") val connectUrl: String,

)