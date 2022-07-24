package com.example.librog.data.remote.data

class AuthResponse(
    val isSuccess : Boolean,
    val code:Int,
    val message:String,
    val result: Result?)

data class Result(
    var userIdx: Int,
    var jwt: String
)

