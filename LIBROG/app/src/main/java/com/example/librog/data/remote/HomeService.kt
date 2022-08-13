package com.example.librog.data.remote

import android.util.Log
import com.example.librog.ApplicationClass
import com.example.librog.ApplicationClass.Companion.retrofit
import com.example.librog.data.remote.data.HomeNoticeResponse
import com.example.librog.data.remote.data.UserDataService
import com.example.librog.data.remote.data.auth.AuthRetrofitInterface
import com.example.librog.ui.main.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object HomeService {

    private val service = retrofit.create(HomeRetrofitInterface::class.java)

    fun getRecommend(fragment: HomeFragment){

        service.getRecommend().enqueue(object: Callback<RecommendResponse> {
            override fun onResponse(call: Call<RecommendResponse>, response: Response<RecommendResponse>) {
                val resp = response.body()!!
                Log.d("Recommend/SUCCESS",resp.code.toString())
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
}