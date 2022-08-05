package com.example.librog.data.remote.data.auth

import android.util.Log
import com.example.librog.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService { //signupview 변수 받음
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView
    val authService = retrofit.create(AuthRetrofitInterface::class.java)

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }
//    //api를 호출하고 관리하는 메서드
    fun signUp(signUpInfo: SignUpInfo){
        //레트로핏, 서비스 객체 생성, api 호출
        authService.signUp(signUpInfo).enqueue(object: Callback<SignUpResponse>{
            //응답 왔을 때 처리
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                val resp: SignUpResponse = response.body()!!
                when(resp.code){
                    1000 ->signUpView.onSignUpSuccess(resp.message) //액티비티에서 상태 처리
                    else ->{
                        signUpView.onSignUpFailure(resp.message)
                    }

                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
            }

        })
    }


    fun kakaoLogin(accessToken: AccessToken){
        //레트로핏, 서비스 객체 생성, api 호출
        authService.kakaoLogin(accessToken).enqueue(object: Callback<AuthResponse2>{
            override fun onResponse(call: Call<AuthResponse2>, response: Response<AuthResponse2>) {
                Log.d("KAKAOLOGIN/SUCCESS",response.toString())
                val resp: AuthResponse2 = response.body()!!
                when(val code = resp.code){
                    1500-> {loginView.onKakaoLoginSuccess(code, resp.result!!)
                        Log.d("KAKAO/SUCCESS", response.body()!!.toString())}
                    else -> {loginView.onKakaoLoginFailure(code, resp.result!!)
                    Log.d("KAKAO/FAILURE", response.body()!!.toString())}

                }
            }

            override fun onFailure(call: Call<AuthResponse2>, t: Throwable) {
                Log.d("KAKAOLOGIN/FAILURE", t.message.toString())

            }

        })
        Log.d("KAKAOLOGIN","HELLO")
    }
}