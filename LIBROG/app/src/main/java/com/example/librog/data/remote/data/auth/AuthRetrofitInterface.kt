package com.example.librog.data.remote.data.auth

import com.example.librog.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users")
    fun signUp(@Body signUpInfo:SignUpInfo): Call<SignUpResponse>


    @POST("/users/kakao/certificate")
    fun kakaoLogin(@Body accessToken: AccessToken): Call<AuthResponse2>
}