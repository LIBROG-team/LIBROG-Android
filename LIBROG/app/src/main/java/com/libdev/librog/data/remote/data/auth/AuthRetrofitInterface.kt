package com.libdev.librog.data.remote.data.auth

import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {
    @POST("/users")
    fun signUp(@Body signUpInfo:SignUpInfo): Call<SignUpResponse>

    @POST("/auth/login")
    fun login(@Body appLoginInfo:AppLoginInfo): Call<AppLoginResponse>

    @POST("/users/kakao/certificate")
    fun kakaoLogin(@Body accessToken: AccessToken): Call<AuthResponse2>

    @PATCH("/users/findMyPassword")
    fun findPwd(@Body findPwdInfo: FindPwdInfo): Call<FindPwdResponse>

    @PATCH("/users/password/change")
    fun changePwd(@Header ("x-access-token") token:String,@Body changePwdInfo: ChangePwdInfo): Call<ChangePwdResponse>

    @DELETE("/users/userDelete/{userIdx}")
    fun deleteUser(@Path("userIdx") userIdx: Int): Call<DeleteUserResponse>
}