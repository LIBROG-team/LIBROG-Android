package com.libdev.librog.ui.main.signup

import android.graphics.Color
import com.libdev.librog.R
import com.libdev.librog.databinding.ActivitySignupBinding
import com.libdev.librog.ui.BaseActivity

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
            //banner 선택 됐을 때
            binding.imgOptionBannerSelected.setBackgroundColor(Color.parseColor("#8A414141"))
            //타이틀 선 색감 조정
            binding.signUpTitleLine.setBackgroundColor(Color.parseColor("#8F8F8F"))
            binding.signUpBackBtn.isEnabled=false
         }
        else {
            binding.imgOptionBannerSelected.setBackgroundColor(Color.TRANSPARENT)
            binding.signUpTitleLine.setBackgroundColor(Color.parseColor("#D9D9D9"))
            binding.signUpBackBtn.isEnabled=true
        }
    }





}