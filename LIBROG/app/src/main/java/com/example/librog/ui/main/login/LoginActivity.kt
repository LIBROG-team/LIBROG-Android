package com.example.librog.ui.main.login

import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.librog.data.local.AppDatabase
import com.example.librog.data.remote.data.auth.*
import com.example.librog.databinding.ActivityLoginBinding
import com.example.librog.ui.BaseActivity
import com.example.librog.ui.main.MainActivity
import com.example.librog.ui.main.mypage.FindPwdActivity
import com.example.librog.ui.main.signup.SignUpActivity
//import com.example.librog.ui.main.signup.SignUpActivity
import com.kakao.sdk.user.UserApiClient


private const val TAG = "LoginActivity"

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView {
    lateinit var name: String
    override fun initAfterBinding() {

        initClickListener()
        val AppDB = AppDatabase.getInstance(this)!!
        val users = AppDB.userDao().getUserList()
        Log.d("userlist",users.toString())

    }

    private fun initClickListener(){
        binding.loginSignUpBtn.setOnClickListener {
            startNextActivity(SignUpActivity::class.java)
        }

        binding.loginLogInBtn.setOnClickListener {
            login()
        }

        binding.loginKakaoSignInBtn.setOnClickListener {
            kakaoLogin()
        }
        binding.loginFindPwdBtn.setOnClickListener {
            startNextActivity(FindPwdActivity::class.java)
        }
    }

    private fun saveIdx(idx:Int){
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("idx",idx)
        editor.apply()
    }

    private fun getAppLoginInfo() : AppLoginInfo {
        val email: String = binding.loginIdEt.text.toString()
        val pwd: String = binding.loginPwdEt.text.toString()

        return AppLoginInfo(email, pwd)
    }

    private fun login(){
        val authService = AuthService()
        authService.setLoginView(this)
        Log.d("login/",getAppLoginInfo().toString())
        authService.login(getAppLoginInfo())
    }


    override fun onLoginSuccess(result: AppLoginResult) {
        saveIdx(result.userIdx)
        finish()
    }

    override fun onLoginFailure(message : String) {
        binding.loginErrorTv.visibility = View.VISIBLE
        binding.loginErrorTv.text = message
    }


    private fun kakaoLogin(){
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            if (error != null) {
                Log.e(TAG, "로그인 실패", error)
            }
            else if (token != null) {
                val authService = AuthService()
                val kakaoAccessToken = AccessToken(token.accessToken)
                authService.setLoginView(this)
                authService.kakaoLogin(kakaoAccessToken)
                Log.d("accesstoken", AccessToken(token.accessToken).toString())
            }
        }
    }

    fun kakaoLogout(){
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
            }

        }
    }

    override fun onKakaoLoginSuccess(code: Int, result: KakaoResult) {
        when (code){
            1500-> {
                showToast("kakao 로그인 성공")
                saveIdx(result.idx)

                //최초 로그인시에만 유저 정보 DB에 저장
                val AppDB = AppDatabase.getInstance(this)!!
                if(!AppDB.userDao().isUserExist(result.idx))
                    AppDB.userDao().insertUserKakaoLogin(result.email,result.idx, result.name, result.profileImgUrl)
                finish()
            }
        }
    }

    override fun onKakaoLoginFailure(code: Int, result: KakaoResult) {
        Log.d("kakaoUser", result.toString())
    }

}