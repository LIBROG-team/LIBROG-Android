package com.example.librog.data.remote.data.auth

import com.google.gson.annotations.SerializedName

class SignUpResponse(
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String,
    )

class AuthResponse2(
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "result")val result: KakaoResult?
)



data class KakaoResult(
    @SerializedName(value = "message")val message : String,
    @SerializedName(value = "idx")val idx : Int,
    @SerializedName(value = "email")val email: String,
    @SerializedName(value = "name")val name: String,
    @SerializedName(value = "profileImgUrl")val profileImgUrl: String,
    @SerializedName(value = "type")val type: String
 )

data class AccessToken(
    @SerializedName(value = "accessToken") val accessToken: String
    )

//회원가입 시 유저가 입력하는 정보
data class SignUpInfo(
    @SerializedName(value = "email")val email: String,
    @SerializedName(value = "password")val password: String,
    @SerializedName(value = "name")val name: String
)


