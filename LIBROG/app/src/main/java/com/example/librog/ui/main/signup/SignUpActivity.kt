package com.example.librog.ui.main.signup

import com.example.librog.R
import com.example.librog.data.remote.data.auth.AuthService
import com.example.librog.data.remote.data.auth.SignUpInfo
import com.example.librog.data.remote.data.auth.SignUpView
import com.example.librog.databinding.ActivitySignupBinding
import com.example.librog.ui.BaseActivity

class SignUpActivity: BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate){
    override fun initAfterBinding() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.sign_up_frame, SignUpConsentFragment())
            .commitAllowingStateLoss()

        binding.signUpBackBtn.setOnClickListener {
            finish()
        }
//        binding.signUpSignUpBtn.setOnClickListener {
////            signUp()
//            finish()
//        }
    }





}