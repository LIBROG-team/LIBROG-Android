package com.libdev.librog.data.remote

import android.util.Log
import com.libdev.librog.ApplicationClass.Companion.retrofit
import com.libdev.librog.ui.main.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object HomeService {

    private val service = retrofit.create(HomeRetrofitInterface::class.java)

    fun getRecommend(fragment: HomeFragment){
        service.getRecommend().enqueue(object: Callback<RecommendResponse> {
            override fun onResponse(call: Call<RecommendResponse>, response: Response<RecommendResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000->{
                        fragment.setRecommend(resp.result)
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<RecommendResponse>, t: Throwable) {
                Log.d("Recommend/FAILURE", t.message.toString())
            }
        })
    }


    fun getRecentBook(fragment: HomeFragment, userId:Int){

        service.getRecentBook(userId).enqueue(object: Callback<RecentReadResponse> {
            override fun onResponse(call: Call<RecentReadResponse>, response: Response<RecentReadResponse>) {
                val resp = response.body()!!
                Log.d("RecentRead/SUCCESS",resp.code.toString())
                when(resp.code){
                    1000->{
                        fragment.setRecentRead(resp.result!!)
                    }
                    else ->{

                    }
                }
            }
            override fun onFailure(call: Call<RecentReadResponse>, t: Throwable) {
                Log.d("RecentRead/FAILURE", t.message.toString())
            }
        })
    }

}