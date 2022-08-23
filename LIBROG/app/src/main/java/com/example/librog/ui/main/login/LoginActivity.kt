package com.example.librog.ui.main.login

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.librog.data.local.AppDatabase
import com.example.librog.data.remote.data.auth.*
import com.example.librog.databinding.ActivityLoginBinding
import com.example.librog.ui.BaseActivity
import com.example.librog.ui.main.MainActivity
import com.example.librog.ui.main.mypage.FindPwdActivity
import com.example.librog.ui.main.signup.SignUpActivity
import com.kakao.sdk.user.UserApiClient


private const val TAG = "LoginActivity"

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView {
    lateinit var name: String
    lateinit var appDB: AppDatabase
    override fun initAfterBinding() {

        initClickListener()
        appDB = AppDatabase.getInstance(this)!!
        val users = appDB.userDao().getUserList()
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

        binding.loginAppLogo.setOnClickListener {
            logout()
        }
    }

    private fun saveUserIdx(idx:Int, type:String){
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("idx",idx)
        editor.putString("type",type)
        editor.apply()
    }

    private fun saveUserToken(token: String){
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putString("token",token)
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
        binding.loginErrorTv.visibility=View.INVISIBLE
        saveUserIdx(result.userIdx,"app")
        saveUserToken(result.jwt)
        Log.d("accessToken/app", result.jwt)
        startNextActivity(MainActivity::class.java)
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
                saveUserToken(token.accessToken)
                showToast(token.accessToken)
                val authService = AuthService()
                val kakaoAccessToken = AccessToken(token.accessToken)
                authService.setLoginView(this)
                authService.kakaoLogin(kakaoAccessToken)
                Log.d("accessToken/kakao", AccessToken(token.accessToken).toString())
            }
        }
    }



    override fun onKakaoLoginSuccess(code: Int, result: KakaoResult) {
        when (code){
            1500-> {
                showToast("kakao 로그인 성공")
                saveUserIdx(result.idx, "kakao")
                //카카오 로그인 최초 한 번만 (카카오 계정 이미지 가져오도록)
                if (!appDB.userDao().isUserExist(result.email))
                    appDB.userDao().insertImgUrl(result.email,"1")
                startNextActivity(MainActivity::class.java)
            }
        }
    }

    override fun onKakaoLoginFailure(code: Int, result: KakaoResult) {
        Log.d("kakaoUser", result.toString())
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

    private fun logout(){
        val spf =getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("idx") //키값에 저장된값 삭제-> idx=-1
        editor.apply()

    }


}