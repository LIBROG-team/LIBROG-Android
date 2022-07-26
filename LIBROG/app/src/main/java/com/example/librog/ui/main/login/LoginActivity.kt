package com.example.librog.ui.main.login

import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.librog.ApplicationClass
import com.example.librog.data.entities.User
import com.kakao.sdk.auth.model.OAuthToken
import com.example.librog.data.remote.data.AuthService
import com.example.librog.data.remote.data.LoginView
import com.example.librog.data.remote.data.Result
import com.example.librog.databinding.ActivityLoginBinding
import com.example.librog.databinding.FragmentHomeBinding
import com.example.librog.databinding.FragmentMypageBinding
import com.example.librog.ui.BaseActivity
import com.example.librog.ui.main.MainActivity
import com.example.librog.ui.main.signup.SignUpActivity
import com.kakao.sdk.user.UserApiClient

private const val TAG = "LoginActivity"

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView {
    lateinit var binding2: FragmentMypageBinding
    override fun initAfterBinding() {
        binding2 = FragmentMypageBinding.inflate(layoutInflater)
        initClickListener()
        updateLoginInfo()
    }

    private fun initClickListener(){
        binding.loginSignUpTv.setOnClickListener {
            startNextActivity(SignUpActivity::class.java)
        }

        binding.loginSignInBtn.setOnClickListener {
            login()
        }

        binding.loginKakaoLogoutTv.setOnClickListener{
            kakaoLogout()
        }

        binding.loginKakaoSignInBtn.setOnClickListener {
            kakaoLogin()
        }
    }

    private fun login(){
        if (binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.loginPasswordEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val email: String = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val pwd: String = binding.loginPasswordEt.text.toString()

//        val userDB = AppDatabase.getInstance(this)!!
//        val user = userDB.userDao().getUser(email,pwd)
//
//        //유저의 인덱스
//        user?.let{
//            Log.d("LOGIN_ACT/GET_USER","userId: ${user.idx}, $user")
//            saveJwt(user.id)
//            //startNextActivity(MainActivity::class.java)
//        }

        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(User(email,pwd,""))

    }


    private fun saveJwt2(jwt:String){
        val spf = getSharedPreferences("auth2", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt",jwt)
        editor.apply()
    }

    override fun onLoginSuccess(code: Int, result: Result) {
        when (code){
            1000-> {
                saveJwt2(result.jwt)
                startNextActivity(MainActivity::class.java)
                Log.d("jwt",result.jwt)
            }
        }
    }

    override fun onLoginFailure() {

    }

    private fun kakaoLogin(){
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            if (error != null) {
                Log.e(TAG, "로그인 실패", error)
            }
            else if (token != null) {
                Log.i(TAG, "로그인 성공 ${token.accessToken}")
                finish()
            }
        }
    }

    private fun kakaoLogout(){
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
            binding.loginNickname.text = null
            binding.loginProfile.setImageBitmap(null)
            binding.loginKakaoSignInBtn.visibility = View.VISIBLE
            binding.loginKakaoLogoutTv.visibility = View.GONE

        }
    }

    private fun updateLoginInfo() {
        // 사용자 정보 요청
        UserApiClient.instance.me { user, error ->
            user?.let {
                Log.i(TAG, "updateLoginInfo: ${user.id} ${user.kakaoAccount?.email} ${user.kakaoAccount?.profile?.nickname} ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                binding.loginNickname.text = user.kakaoAccount?.profile?.nickname
                Glide.with(this).load(user.kakaoAccount?.profile?.thumbnailImageUrl).circleCrop().into(binding.loginProfile)
                binding.loginKakaoSignInBtn.visibility = View.GONE
                binding.loginKakaoLogoutTv.visibility = View.VISIBLE
            }
            error?.let {
                binding.loginNickname.text = null
                binding.loginProfile.setImageBitmap(null)
                binding.loginKakaoSignInBtn.visibility = View.VISIBLE
                binding.loginKakaoLogoutTv.visibility = View.GONE
            }
        }
    }

}