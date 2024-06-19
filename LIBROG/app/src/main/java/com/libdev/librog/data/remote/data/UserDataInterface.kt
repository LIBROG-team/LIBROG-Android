package com.libdev.librog.data.remote.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserDataInterface {
    @GET("/records/statistics/{userIdx}")
    fun getUserStat(@Path("userIdx") userIdx: Int): Call<UserStatResponse>

    @GET("/contents/notice")
    fun getHomeNotice(): Call<HomeNoticeResponse>

    @GET("/users/profile/{userIdx}")
    fun getUserProfile(@Path("userIdx") userIdx: Int): Call<UserProfileResponse>

    @PATCH("users/profile/edit")
    fun editProfile(@Body editProfileInfo: EditProfileInfo): Call<EditProfileResponse>
}