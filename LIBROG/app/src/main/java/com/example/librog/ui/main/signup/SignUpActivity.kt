package com.example.librog.ui.main.signup

import android.graphics.Color
import android.view.View
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

    }

    fun fadeBackground(isFaded:Boolean){
        if (isFaded){
            binding.imgOptionBannerSelected.setBackgroundColor(Color.parseColor("#8A414141"))
            //색감 조정
            binding.signUpTitleLine.setBackgroundColor(Color.parseColor("#8F8F8F"))
         }
        else {
            binding.imgOptionBannerSelected.setBackgroundColor(Color.TRANSPARENT)
            binding.signUpTitleLine.setBackgroundColor(Color.parseColor("#D9D9D9"))
        }
    }





}