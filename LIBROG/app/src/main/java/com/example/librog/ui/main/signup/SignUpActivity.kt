package com.example.librog.ui.main.signup

import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.librog.data.entities.User
import com.example.librog.data.local.AppDatabase
import com.example.librog.data.remote.data.*
import com.example.librog.databinding.ActivitySignupBinding
import com.example.librog.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

//class SignUpActivity: BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate), SignUpView {
//    override fun initAfterBinding() {
//
//        binding.signUpSignUpBtn.setOnClickListener {
//            signUp()
//            finish()
//        }
//    }
//
//    private fun getUser() : User {
//        val email: String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
//        val pwd: String = binding.signUpPasswordEt.text.toString()
//        var name: String = binding.signUpNameEt.text.toString()
//
//        return User(email, pwd,name)
//    }
//
//
////    private fun signUp(){
////        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
////            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
////            return
////        }
////
////        if (binding.signUpNameEt.text.toString().isEmpty()) {
////            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
////            return
////        }
////
////        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
////            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
////            return
////        }
////
////
////        val authService = AuthService()
////        authService.setSignUpView(this)
////
////        authService.signUp(getUser()) //api호출
////    }
//
////    override fun onSignUpSuccess() {
////        finish()
////    }
////
////    override fun onSignUpFailure() {
////
////    }
//}