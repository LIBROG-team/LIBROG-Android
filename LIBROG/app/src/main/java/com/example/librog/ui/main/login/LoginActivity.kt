package com.example.librog.ui.main.login

import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.librog.data.local.AppDatabase
import com.example.librog.data.remote.data.AccessToken
import com.example.librog.data.remote.data.AuthService
import com.example.librog.data.remote.data.KakaoResult
import com.example.librog.data.remote.data.LoginView
import com.example.librog.databinding.ActivityLoginBinding
import com.example.librog.databinding.FragmentMypageBinding
import com.example.librog.ui.BaseActivity
//import com.example.librog.ui.main.signup.SignUpActivity
import com.kakao.sdk.user.UserApiClient


private const val TAG = "LoginActivity"

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView {
    lateinit var binding2: FragmentMypageBinding
    lateinit var email: String
    lateinit var name: String
    override fun initAfterBinding() {
        binding2 = FragmentMypageBinding.inflate(layoutInflater)
        initClickListener()
        updateLoginInfo()
        val AppDB = AppDatabase.getInstance(this)!!
        val users = AppDB.userDao().getUserList()
        Log.d("userlist",users.toString())

    }

    private fun initClickListener(){
//        binding.loginSignUpTv.setOnClickListener {
//            startNextActivity(SignUpActivity::class.java)
//        }

        binding.loginSignInBtn.setOnClickListener {
//            login()
        }

        binding.loginKakaoLogoutTv.setOnClickListener{
            kakaoLogout()
        }

        binding.loginKakaoSignInBtn.setOnClickListener {
            kakaoLogin()
        }
    }

//    private fun login(){
//        if (binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()) {
//            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if (binding.loginPasswordEt.text.toString().isEmpty()) {
//            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val email: String = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
//        val pwd: String = binding.loginPasswordEt.text.toString()
//
////        val userDB = AppDatabase.getInstance(this)!!
////        val user = userDB.userDao().getUser(email,pwd)
////
////        //유저의 인덱스
////        user?.let{
////            Log.d("LOGIN_ACT/GET_USER","userId: ${user.idx}, $user")
////            saveJwt(user.id)
////            //startNextActivity(MainActivity::class.java)
////        }
//
//        val authService = AuthService()
//        authService.setLoginView(this)
//
//        authService.login(User(email,pwd,""))
//
//    }


    private fun saveJwt2(jwt:String){
        val spf = getSharedPreferences("auth2", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt",jwt)
        editor.apply()
    }

//    override fun onLoginSuccess(code: Int, result: Result) {
//        when (code){
//            1000-> {
//                saveJwt2(result.jwt)
//                showToast("로그인 성공")
//                startNextActivity(MainActivity::class.java)
//                Log.d("jwt",result.jwt)
//            }
//        }
//    }
//
//    override fun onLoginFailure(code: Int, result:Result) {
//        when (code){
//            1500->{showToast("이메일을 입력해주세요")}
//            2019->{showToast("존재하지 않는 계정입니다")}
//        }
//
//    }



    private fun kakaoLogin(){
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            if (error != null) {
                Log.e(TAG, "로그인 실패", error)
            }
            else if (token != null) {
                Log.i(TAG, "로그인 성공 ${token.accessToken}")

                Log.d("accesstoken",token.accessToken)

                //데이터 클래스
                val authService = AuthService()
                val kakaoAccessToken = AccessToken(token.accessToken)
                authService.setLoginView(this)
                authService.kakaoLogin(kakaoAccessToken)
                Log.d("accesstoken",AccessToken(token.accessToken).toString())
                updateLoginInfo()
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

    override fun onKakaoLoginSuccess(code: Int, result: KakaoResult) {
        when (code){
            1500-> {
                //showToast("kakao 로그인 성공")
                Log.d("kakaoLogin",result.toString())
                Log.d("kakaoLogin",code.toString())
                val AppDB = AppDatabase.getInstance(this)!!
                if(!AppDB.userDao().isUserExist(result.idx))
                    {   Log.d("kakaoLogin","데이터 삽입")
                        AppDB.userDao().insertUserKakaoLogin(result.email,result.idx, result.name, result.profileImgUrl)}

            }
        }
    }

    override fun onKakaoLoginFailure(code: Int, result: KakaoResult) {
        Log.d("kakaoUser", result.toString())
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

                name = user.kakaoAccount?.profile?.nickname.toString()
                email = user.kakaoAccount?.email.toString()
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