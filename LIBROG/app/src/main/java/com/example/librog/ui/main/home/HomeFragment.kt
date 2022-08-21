package com.example.librog.ui.main.home

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.librog.ApplicationClass
import com.example.librog.R
import com.example.librog.data.remote.*
import com.example.librog.data.remote.data.*
import com.example.librog.databinding.FragmentHomeBinding
import com.example.librog.ui.BaseFragment
import com.example.librog.ui.main.MainActivity
import com.example.librog.ui.main.history.DetailHistoryActivity
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val service= UserDataService
    private val homeService = HomeService
    private val mainPotService = ApplicationClass.retrofit.create(HomeRetrofitInterface::class.java)


    override fun initAfterBinding() {
        (activity as MainActivity).showBottomNav()

        val userIdx = getUserIdx(this)
        service.getUserNotice(this)
        homeService.getRecommend(this)
        homeService.getRecentBook(this)

        getMainPot()
    }

    override fun onStart() {
        super.onStart()
        service.getUserNotice(this)
    }

    fun setNotice(result: ArrayList<HomeNoticeResult>){
        val bannerAdapter = HomeNoticeVPAdapter(this)

        for (item in result){
            bannerAdapter.addFragment(HomeNoticeFragment(item))
        }

        binding.homeBannerNoticeVp.adapter = bannerAdapter
        binding.homeBannerNoticeVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        setIndicator()
    }

    fun setRecommend(result: ArrayList<RecommendResult>){
        val recommendRVAdapter = RecommendRVAdapter(result)
        //리사이클러뷰에 어댑터 연결
        binding.homeBannerRecommendRv.adapter = recommendRVAdapter
        binding.homeBannerRecommendRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        recommendRVAdapter.setMyItemClickListener(object : RecommendRVAdapter.OnItemClickListener {
            override fun onItemClick(recommendData: RecommendResult) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recommendData.connectUrl))
                startActivity(intent)
            }
        })

        binding.homeBannerRecommendRv.layoutManager = GridLayoutManager(context, 2)
    }

    fun setRecentRead(result: ArrayList<RecentReadResult>){
        val recentReadRVAdapter = RecentReadRVAdapter(result)
        //리사이클러뷰에 어댑터 연결
        binding.homeRecentreadBookRv.adapter = recentReadRVAdapter
        binding.homeRecentreadBookRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        recentReadRVAdapter.setMyItemClickListener(object : RecentReadRVAdapter.OnItemClickListener {
            override fun onItemClick(recentReadResult: RecentReadResult) {
                val intent = Intent(context, DetailHistoryActivity::class.java)
                intent.putExtra("readingRecordIdx", recentReadResult.readingRecordIdx)
                startActivity(intent)
            }
        })
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

    private fun getMainPot(){
        mainPotService.getMainPot(getIdx()).enqueue(object: Callback<MainPotResponse> {
            override fun onResponse(call: Call<MainPotResponse>, response: Response<MainPotResponse>) {
                val resp = response.body()!!
                Log.d("getMainPot",resp.code.toString())
                when (resp.code){
                    1000->{
                        setMainPot(resp.result!!)
                    }
                }
            }
            override fun onFailure(call: Call<MainPotResponse>, t: Throwable) {
            }
        })
    }

    private fun setMainPot(result: MainPotResult){
        val bgColor = result.backgroundColor ?: "#D9D9D9"
        binding.homeFlowerTv.text = result.name
        Glide.with(this).load(result.flowerImgUrl).into(binding.homeCircleFlowerImg)
        binding.mainCircleFlowerIv.backgroundTintList = ColorStateList.valueOf(Color.parseColor(bgColor))

    }

    private fun getIdx(): Int{
        val spf = activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx",-1)
    }


    private fun getUserIdx(fragment: Fragment): Int {
        val spf =
            fragment.activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx", -1)
    }

}
