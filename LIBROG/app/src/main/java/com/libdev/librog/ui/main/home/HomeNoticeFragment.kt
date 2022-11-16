package com.libdev.librog.ui.main.home

import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide
import com.libdev.librog.data.remote.data.HomeNoticeResult
import com.libdev.librog.databinding.FragmentHomeNoticeBinding
import com.libdev.librog.ui.BaseFragment

class HomeNoticeFragment(val item:HomeNoticeResult): BaseFragment<FragmentHomeNoticeBinding>(FragmentHomeNoticeBinding::inflate) {
    override fun initAfterBinding() {
        Glide.with(this).load(item.noticeImgUrl).into(binding.homeBannerNoticeIv)
        binding.homeBannerNoticeIv.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.connectUrl))
            startActivity(intent)
        }
    }



}