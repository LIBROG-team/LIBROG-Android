package com.example.librog.data.remote.data.auth

interface LoginView {
//    fun onLoginSuccess(code : Int, result: Result)
//    fun onLoginFailure(code: Int, result:Result)

    fun onKakaoLoginSuccess(code: Int, result: KakaoResult)
    fun onKakaoLoginFailure(code: Int, result: KakaoResult)
}