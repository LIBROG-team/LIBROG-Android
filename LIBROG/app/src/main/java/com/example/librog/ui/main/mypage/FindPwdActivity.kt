package com.example.librog.ui.main.mypage

import com.example.librog.databinding.ActivityFindPwdBinding
import com.example.librog.ui.BaseActivity

class FindPwdActivity: BaseActivity<ActivityFindPwdBinding>(ActivityFindPwdBinding::inflate) {
    override fun initAfterBinding() {
        binding.findPwdBackBtn.setOnClickListener {
            finish()
        }
    }
}