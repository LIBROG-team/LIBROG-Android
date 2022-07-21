package com.example.librog.ui.main.addFlowerpot

import com.example.librog.data.entities.FlowerData
import com.example.librog.databinding.ActivityDetailUnlockedFpBinding
import com.example.librog.ui.BaseActivity

class DetailUnlockedFpActivity :
    BaseActivity<ActivityDetailUnlockedFpBinding>(ActivityDetailUnlockedFpBinding::inflate) {

    override fun initAfterBinding() {
        binding.apply {
            detailUnlockedBackBtnIv.setOnClickListener {
                finish()
            }

            detailUnlockedSelectIv
        }
    }

}