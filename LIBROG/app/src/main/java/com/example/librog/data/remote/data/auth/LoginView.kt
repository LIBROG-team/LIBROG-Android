package com.example.librog.data.remote.data.auth

interface LoginView {
    fun onLoginSuccess(result: AppLoginResult)
    fun onLoginFailure(message: String)

    fun onKakaoLoginSuccess(code: Int, result: KakaoResult)
    fun onKakaoLoginFailure(code: Int, result: KakaoResult)
}