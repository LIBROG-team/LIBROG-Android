package com.example.librog.data.remote.data

import android.util.Log
import com.example.librog.ApplicationClass.Companion.retrofit
import com.example.librog.ui.main.mypage.MypageFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object UserDataService {

    private val userDataService=retrofit.create(UserDataInterface::class.java)
    private val userIdx = 1

    fun getUserStat(fragment: MypageFragment){

        userDataService.getUserStat(userIdx).enqueue(object: Callback<UserStatResponse> {
            override fun onResponse(call: Call<UserStatResponse>, response: Response<UserStatResponse>) {
                val resp = response.body()!!
                Log.d("GETUSERDATA/SUCCESS",resp.code.toString())
                when(val code = resp.code){
                    1000->{
                        fragment.setData(resp.result, code)
                    }
                    else ->{

                    }
                }
            }

            override fun onFailure(call: Call<UserStatResponse>, t: Throwable) {
                Log.d("GETUSERDATA/FAILURE", t.message.toString())

            }

        })
        Log.d("GETUSERDATA","HELLO")
    }
}