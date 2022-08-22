package com.example.librog.data.remote.data

import android.util.Log
import com.example.librog.ApplicationClass.Companion.retrofit
import com.example.librog.ui.main.home.HomeFragment
import com.example.librog.ui.main.mypage.MypageFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object UserDataService {

    private val userDataService=retrofit.create(UserDataInterface::class.java)


    fun getUserNotice(fragment: HomeFragment){

        userDataService.getHomeNotice().enqueue(object: Callback<HomeNoticeResponse> {
            override fun onResponse(call: Call<HomeNoticeResponse>, response: Response<HomeNoticeResponse>) {
                val resp = response.body()!!
                Log.d("HomeNotice/SUCCESS",resp.code.toString())
                when(resp.code){
                    1000->{
                        fragment.setNotice(resp.result!!)
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<HomeNoticeResponse>, t: Throwable) {
                Log.d("HomeNotice/FAILURE", t.message.toString())
            }
        })
    }


//    fun getRecentBook(fragment: HomeFragment){
//        val userIdx = 34 //임시
//        userDataService.getRecentRead(userIdx).enqueue(object: Callback<List<RecentReadResponse>> {
//            override fun onResponse(call: Call<List<RecentReadResponse>>, response: Response<List<RecentReadResponse>>) {
//                val resp = response.body()!!
//
//                if (resp[0].code==null ||resp[0].code==1000){
//                    fragment.setRecentRead(resp[0].result)
//                }
//                else {
//                    Log.d("GETRECENTBOOK", "유저코드 존재x")
//                }
//            }
//            override fun onFailure(call: Call<List<RecentReadResponse>>, t: Throwable) {
//                Log.d("GETRECENTBOOK", t.message.toString())
//            }
//        })
//    }



}