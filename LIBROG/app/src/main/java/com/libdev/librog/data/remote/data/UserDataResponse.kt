package com.libdev.librog.data.remote.data

import com.google.gson.annotations.SerializedName

//마이페이지 유저 통계
data class UserStatResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: UserStatResult
)

data class UserStatResult(
    @SerializedName("userIdx") var userIdx: Int,
    @SerializedName("flowerCnt") var flowerCnt: Int,
    @SerializedName("readingCnt") var readingCnt: Int,
    @SerializedName("starRatingCnt") var starRatingCnt: Int,
    @SerializedName("quoteCnt") var quoteCnt: Int,
    @SerializedName("contentCnt") var contentCnt: Int
)


//홈 배너
data class HomeNoticeResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<HomeNoticeResult>?
)

data class HomeNoticeResult(
    @SerializedName("idx") val idx: Int,
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("connectUrl") val connectUrl: String,
    @SerializedName("noticeImgUrl") val noticeImgUrl: String
)


//마이페이지 유저 프로필 조회
data class UserProfileResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: UserProfileResult?
)


data class UserProfileResult(
    @SerializedName("idx") val idx: Int,
    @SerializedName("profileImgUrl") val profileImgUrl: String,
    @SerializedName("name") val name: String,
    @SerializedName("introduction") val introduction: String,
    @SerializedName("type") val type: String?
)


//자기소개 변경
data class EditProfileResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
)

data class EditProfileInfo(
    @SerializedName("idx") val idx: Int,
    @SerializedName("name") val name: String,
    @SerializedName("introduction") val introduction: String,
    @SerializedName("profileImg") val profileImg: String?
)





