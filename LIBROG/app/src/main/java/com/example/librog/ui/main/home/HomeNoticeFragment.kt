package com.example.librog.ui.main.home

import com.example.librog.databinding.FragmentHomeNoticeBinding
import com.example.librog.ui.BaseFragment

class HomeNoticeFragment(val imgRes : Int): BaseFragment<FragmentHomeNoticeBinding>(FragmentHomeNoticeBinding::inflate) {
    override fun initAfterBinding() {
        binding.homeBannerNoticeVp.setImageResource(imgRes)
    }



}