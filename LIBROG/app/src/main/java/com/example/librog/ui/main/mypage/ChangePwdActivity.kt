package com.example.librog.ui.main.mypage

import com.example.librog.databinding.ActivityChangePwdBinding
import com.example.librog.ui.BaseActivity

class ChangePwdActivity: BaseActivity<ActivityChangePwdBinding>(ActivityChangePwdBinding::inflate) {
    override fun initAfterBinding(){
        binding.changePwdBackBtn.setOnClickListener {
            finish()
        }
    }
}