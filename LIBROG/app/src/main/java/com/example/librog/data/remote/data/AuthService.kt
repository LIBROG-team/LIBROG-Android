package com.example.librog.data.remote.data

import android.util.Log
import com.example.librog.data.entities.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService { //signupview 변수 받음
    private lateinit var signUpView: SignUpView

    fun setSignUpview(signUpView: SignUpView){
        this.signUpView = signUpView
    }
    //api를 호출하고 관리하는 메서드
    fun signUp(user : User){
        //레트로핏, 서비스 객체 생성, api 호출
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(user).enqueue(object: Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("SIGNUP/SUCCESS",response.toString())
                val resp: AuthResponse = response.body()!!
                when(resp.code){
                    1000 ->signUpView.onSignUpSuccess() //액티비티에서 상태 처리
                    else->signUpView.onSignUpFailure() 
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }

        })
        Log.d("SIGNUP","HELLO")
    }
}