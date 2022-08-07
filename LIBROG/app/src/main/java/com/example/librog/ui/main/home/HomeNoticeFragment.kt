package com.example.librog.ui.main.home

import com.example.librog.databinding.FragmentHomeAdBinding
import com.example.librog.ui.BaseFragment

class HomeNoticeFragment(val imgRes : Int): BaseFragment<FragmentHomeAdBinding>(FragmentHomeAdBinding::inflate) {
    override fun initAfterBinding() {
        binding.homeBannerNoticeVp.setImageResource(imgRes)
    }

}