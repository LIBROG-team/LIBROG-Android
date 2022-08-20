package com.example.librog.ui.main.home

import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide
import com.example.librog.data.remote.data.HomeNoticeResult
import com.example.librog.databinding.FragmentHomeNoticeBinding
import com.example.librog.ui.BaseFragment

class HomeNoticeFragment(val item:HomeNoticeResult): BaseFragment<FragmentHomeNoticeBinding>(FragmentHomeNoticeBinding::inflate) {
    override fun initAfterBinding() {
        Glide.with(this).load(item.noticeImgUrl).into(binding.homeBannerNoticeIv)
        binding.homeBannerNoticeIv.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.connectUrl))
            startActivity(intent)
        }
    }



}