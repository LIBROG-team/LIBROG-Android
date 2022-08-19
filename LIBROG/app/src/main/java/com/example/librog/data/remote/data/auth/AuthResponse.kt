package com.example.librog.data.remote.data.auth

import com.google.gson.annotations.SerializedName

//회원가입(응답값)
data class SignUpResponse(
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "result")val result:SignUpResult?,
    )

data class SignUpResult(
    @SerializedName(value = "createdUserIdx") val createdUserIdx: Int
)

//회원가입(입력값)
data class SignUpInfo(
    @SerializedName(value = "email") val email: String,
    @SerializedName(value = "password") val password: String,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "profileImg") val profileImg: String,
    @SerializedName(value = "introduction") val introduction: String,
)



//카카오 로그인
data class AuthResponse2(
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

//카카오 로그인(입력값)
data class AccessToken(
    @SerializedName(value = "accessToken") val accessToken: String
)


//앱 로그인(응답값)
data class AppLoginResponse(
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "result")val result: AppLoginResult?
)

data class AppLoginResult(
    @SerializedName(value = "jwt")val jwt : String,
    @SerializedName(value = "userIdx")val userIdx : Int
)

//앱 로그인(입력값)
data class AppLoginInfo(
    @SerializedName(value = "email")val email: String,
    @SerializedName(value = "password")val password: String
)


//비밀번호 찾기(응답값)
data class FindPwdResponse(
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "result")val result: FindPwdResult?
)

data class FindPwdResult(
    @SerializedName("fieldCount") val fieldCount: Int,
    @SerializedName("affectedRows") val affectedRows: Int,
    @SerializedName("insertId") val insertId: Int,
    @SerializedName("info") val info: String,
    @SerializedName("serverStatus") val serverStatus: Int,
    @SerializedName("warningStatus") val warningStatus: Int,
    @SerializedName("changedRows") val changedRows: Int
)

//비밀번호 찾기(입력값)
data class FindPwdInfo(
    @SerializedName(value = "email") val email: String
)


//비밀번호 변경(응답값)
data class ChangePwdResponse(
    @SerializedName(value = "isSuccess")val isSuccess : Boolean,
    @SerializedName(value = "code")val code:Int,
    @SerializedName(value = "message")val message:String,
    @SerializedName(value = "result")val result: ChangePwdResult?
)

data class ChangePwdResult(
    @SerializedName(value = "userIdx")val userIdx : Int,
    @SerializedName(value = "newPassword")val newPassword:String
)

data class ChangePwdInfo(
    @SerializedName(value = "userIdx")val userIdx : Int,
    @SerializedName(value = "oldPassword")val oldPassword:String,
    @SerializedName(value = "newPassword")val newPassword:String
)








