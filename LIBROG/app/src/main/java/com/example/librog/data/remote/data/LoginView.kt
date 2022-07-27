package com.example.librog.data.remote.data

interface LoginView {
    fun onLoginSuccess(code : Int, result: Result)
    fun onLoginFailure()
}