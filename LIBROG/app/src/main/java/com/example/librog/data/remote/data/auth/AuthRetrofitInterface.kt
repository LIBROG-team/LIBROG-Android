package com.example.librog.data.remote.data.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users")
    fun signUp(@Body signUpInfo:SignUpInfo): Call<SignUpResponse>

    @POST("/auth/login")
    fun login(@Body appLoginInfo:AppLoginInfo): Call<AppLoginResponse>

    @POST("/users/kakao/certificate")
    fun kakaoLogin(@Body accessToken: AccessToken): Call<AuthResponse2>
}