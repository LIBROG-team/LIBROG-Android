package com.example.librog.ui.main.home

import android.content.Intent
import android.view.ViewGroup

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.librog.R
import com.example.librog.data.ReadBook
import com.example.librog.databinding.FragmentHomeBinding
import com.example.librog.ui.BaseFragment
import com.example.librog.ui.main.MainActivity
import com.example.librog.ui.main.addbook.AddBookSelectActivity
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private var readBookData = ArrayList<ReadBook>() //album data class

    override fun initAfterBinding() {
        (activity as MainActivity).showBottomNav()

        readBookData.apply{
            add(ReadBook(R.drawable.home_item_book1,"노르웨이의 숲","무라카미 하루키","2022.06.28"))
            add(ReadBook(R.drawable.home_item_book2,"공정하다는 착각","마이크 센델","2022.05.06"))
        }

        initRVAdapter()
        initNoticeVp()

    }
    private fun initRVAdapter(){
        val readbookRVAdapter = ReadBookRVAdapter(readBookData)
        //리사이클러뷰에 어댑터 연결
        binding.homeRecentreadBookRv.adapter = readbookRVAdapter
        binding.homeRecentreadBookRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        readbookRVAdapter.setMyItemClickListener(object : ReadBookRVAdapter.OnItemClickListener {
            override fun onItemClick(tempReadBookData: ReadBook) {
                startActivity(Intent(context, AddBookSelectActivity::class.java))
            }
        })
    }

    private fun initNoticeVp(){
        val bannerAdapter = HomeNoticeVPAdapter(this)
        //추가할 프래그먼트를 넣어줌
        bannerAdapter.addFragment(HomeNoticeFragment(R.drawable.home_banner_notice_temp))
        bannerAdapter.addFragment(HomeNoticeFragment(R.drawable.home_banner_notice_temp))
        bannerAdapter.addFragment(HomeNoticeFragment(R.drawable.home_banner_notice_temp))

        binding.homeBannerNoticeVp.adapter = bannerAdapter
        binding.homeBannerNoticeVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        setIndicator()
    }

    private fun setIndicator(){
        val viewPager2 = binding.homeBannerNoticeVp
        val tabLayout = binding.homeTabLayout

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
        }.attach()

        for (i in 0 until binding.homeTabLayout.tabCount) {
            val tab = (binding.homeTabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(0, 0, 20, 0)
            tab.requestLayout()
        }
    }


}