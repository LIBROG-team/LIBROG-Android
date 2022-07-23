package com.example.librog.ui.main.signup

import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.librog.data.entities.User
import com.example.librog.data.local.AppDatabase
import com.example.librog.data.remote.data.AuthResponse
import com.example.librog.data.remote.data.AuthRetrofitInterface
import com.example.librog.data.remote.data.getRetrofit
import com.example.librog.databinding.ActivitySignupBinding
import com.example.librog.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignUpActivity: BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate) {
    override fun initAfterBinding() {

        binding.signUpSignUpBtn.setOnClickListener {
            signUp()
            finish()
        }
    }

    private fun getUser() : User {
        val email: String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd: String = binding.signUpPasswordEt.text.toString()
        var name: String = binding.signUpNameEt.text.toString()

        return User(email, pwd, name)
    }

//    private fun signUp(){
//        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
//            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
//            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val userDB = AppDatabase.getInstance(this)!!
//        userDB.userDao().insert(getUser())
//
//        //로그
//        val user = userDB.userDao().getUserList()
//        Log.d("SIGNUPACT", user.toString())
//    }

    private fun signUp(){
        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpNameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        //레트로핏, 서비스 객체 생성, api 호출
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(getUser()).enqueue(object: Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("SIGNUP/SUCCESS", response.toString())
                val resp: AuthResponse = response.body()!! //response안에서 body값 가져옴 (서버에서 보낸값)
                when(resp.code){
                    1000->finish()
//                    2016, 2018 -> { //이메일 관련 오류
//                        binding.signUpEmailErrorTv.visibility = View.VISIBLE
//                        binding.signUpEmailErrorTv.text = resp.message //서버에서 보낸 메세지
//                    }
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }

        })
        Log.d("SIGNUP","HELLO")

    }
}