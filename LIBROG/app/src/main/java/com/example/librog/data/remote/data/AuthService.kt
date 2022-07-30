package com.example.librog.data.remote.data

import android.util.Log
import com.example.librog.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService { //signupview 변수 받음
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }
//    //api를 호출하고 관리하는 메서드
//    fun signUp(user : User){
//        //레트로핏, 서비스 객체 생성, api 호출
//        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
//        authService.signUp(user).enqueue(object: Callback<AuthResponse>{
//            //응답 왔을 때 처리
//            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
//                Log.d("SIGNUP/SUCCESS",response.toString())
//                val resp: AuthResponse = response.body()!!
//                when(resp.code){
//                    1000 ->signUpView.onSignUpSuccess() //액티비티에서 상태 처리
//                    else ->{
//                        signUpView.onSignUpFailure()
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
//                Log.d("SIGNUP/FAILURE", t.message.toString())
//            }
//
//        })
//        Log.d("SIGNUP","HELLO")
//    }
//
//    fun login(user : User){
//        //레트로핏, 서비스 객체 생성, api 호출
//        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
//        authService.login(user).enqueue(object: Callback<AuthResponse>{
//            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
//                Log.d("LOGIN/SUCCESS",response.toString())
//                val resp: AuthResponse = response.body()!!
//                when(val code =resp.code){
//                    1000-> loginView.onLoginSuccess(code, resp.result!!)
//                    else -> loginView.onLoginFailure(code, resp.result!!)
//                }
//            }
//
//            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
//                Log.d("LOGIN/FAILURE", t.message.toString())
//            }
//
//        })
//        Log.d("LOGIN","HELLO")
//    }

    fun kakaoLogin(accessToken: AccessToken){
        //레트로핏, 서비스 객체 생성, api 호출
        val authService = retrofit.create(AuthRetrofitInterface::class.java)
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