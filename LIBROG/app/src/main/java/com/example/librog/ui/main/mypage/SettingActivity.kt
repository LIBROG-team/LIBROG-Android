package com.example.librog.ui.main.mypage

import com.example.librog.ApplicationClass
import com.example.librog.data.remote.data.UserDataInterface
import com.example.librog.data.remote.data.auth.AuthRetrofitInterface
import com.example.librog.databinding.ActivitySettingBinding
import com.example.librog.ui.BaseActivity

class SettingActivity: BaseActivity<ActivitySettingBinding>(ActivitySettingBinding::inflate) {

    override fun initAfterBinding() {
        binding.settingBackBtn.setOnClickListener {
            finish()
        }
        binding.settingProfileBtn.setOnClickListener {
            startNextActivity(EditProfileActivity::class.java)
        }
    }
}