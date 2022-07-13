package com.example.librog.ui.main.addFlowerpot

import com.example.librog.databinding.ActivityAddFlowerpotBinding
import com.example.librog.ui.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator

class AddFlowerpotActivity : BaseActivity<ActivityAddFlowerpotBinding>(ActivityAddFlowerpotBinding::inflate) {
    private val tabLayoutTextList = arrayListOf("획득한 화분", "미획득 화분")

    override fun initAfterBinding() {
        val vpAdapter = AddFlowerpotVPAdapter(this)
        binding.addFlowerpotVp.adapter = vpAdapter
        binding.addFlowerpotBackBtnIv.setOnClickListener {
            finish()
        }
        TabLayoutMediator(binding.addFlowerpotTb, binding.addFlowerpotVp){ tab, position->
            tab.text =tabLayoutTextList[position]
        }.attach()

    }
}
