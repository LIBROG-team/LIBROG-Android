package com.example.librog.ui.main.login

import android.util.Log
import android.widget.Toast
import com.example.librog.data.entities.User
import com.example.librog.data.local.AppDatabase
import com.example.librog.data.remote.data.AuthService
import com.example.librog.data.remote.data.LoginView
import com.example.librog.data.remote.data.Result
import com.example.librog.databinding.ActivityLoginBinding
import com.example.librog.ui.BaseActivity
import com.example.librog.ui.main.MainActivity
import com.example.librog.ui.main.signup.SignUpActivity


class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView {
    override fun initAfterBinding() {
        binding.loginSignUpTv.setOnClickListener {
            startNextActivity(SignUpActivity::class.java)
        }

        binding.loginSignInBtn.setOnClickListener {
            login()
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

        authService.login(User("",email,pwd))

        showToast("회원 정보가 존재하지 않습니다")
    }

//    //인자값으로 받은 아이디(Jwt) 저장
//    private fun saveJwt(jwt:Int){
//        val spf = getSharedPreferences("auth", MODE_PRIVATE)
//        val editor = spf.edit()
//
//        editor.putInt("jwt",jwt)
//        editor.apply()
//    }

    private fun saveJwt2(jwt:String){
        val spf = getSharedPreferences("auth2", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt",jwt)
        editor.apply()
    }

    override fun onLoginSuccess(code: Int, result: Result) {
        when (code){
            1000-> {saveJwt2(result.jwt)
                    startNextActivity(MainActivity::class.java)}
        }
    }

    override fun onLoginFailure() {

    }

}