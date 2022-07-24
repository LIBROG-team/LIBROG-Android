package com.example.librog.data.remote.data

import com.example.librog.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users")
    fun signUp(@Body user: User): Call<AuthResponse>

    @POST("/users/login") //추후 수정
    fun login(@Body user: User): Call<AuthResponse>
}