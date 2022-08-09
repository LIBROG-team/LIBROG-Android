package com.example.librog.ui.main.home

import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide
import com.example.librog.databinding.FragmentHomeNoticeBinding
import com.example.librog.ui.BaseFragment

class HomeNoticeFragment(private val imgRes : String, private val connectUrl:String): BaseFragment<FragmentHomeNoticeBinding>(FragmentHomeNoticeBinding::inflate) {
    override fun initAfterBinding() {
        Glide.with(this).load(imgRes).into(binding.homeBannerNoticeVp)
        binding.homeBannerNoticeVp.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(connectUrl))
            startActivity(intent)
        }
    }



}