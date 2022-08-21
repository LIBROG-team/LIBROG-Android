package com.example.librog.ui.main.addFlowerpot

import androidx.appcompat.app.AppCompatActivity
import com.example.librog.databinding.ActivityDetailUnlockedFpBinding
import com.example.librog.ui.BaseActivity

class DetailUnlockedFpActivity : BaseActivity<ActivityDetailUnlockedFpBinding>(ActivityDetailUnlockedFpBinding::inflate) {

    override fun initAfterBinding() {
        val userIdx = getUserIdx()

        initClickListener(userIdx)
    }

    private fun initClickListener(userIdx: Int) {
        binding.detailUnlockedBackBtnIv.setOnClickListener {
            finish()
        }


    }


    private fun getUserIdx(): Int{
        val spf = getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx",-1)
    }

}