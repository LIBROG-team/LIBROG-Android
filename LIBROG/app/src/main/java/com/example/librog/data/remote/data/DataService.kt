package com.example.librog.data.remote.data

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.librog.ApplicationClass.Companion.retrofit
import com.example.librog.ui.main.addFlowerpot.LockedFlowerpotFragment
import com.example.librog.ui.main.addFlowerpot.UnlockedFlowerpotFragment
import com.example.librog.ui.main.flowerpot.FlowerpotFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataService {
    private val dataService = retrofit.create(DataInterface::class.java)


    private fun getUserIdx(fragment: Fragment): Int{
        val spf = fragment.activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx",-1)
    }


    //화분 정보 가져오기
    fun getFpList(flowerpotFragment: FlowerpotFragment) {
        val userIdx = getUserIdx(flowerpotFragment)

        dataService.getFpList(userIdx).enqueue(object : Callback<DataResponse1> {
            override fun onResponse(call: Call<DataResponse1>, response: Response<DataResponse1>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        Log.d("resp", resp.result.size.toString())
                        flowerpotFragment.setData(resp.result)
                    }

                    2019 -> {

                    }
                    2020 -> {

                    }
                    else -> {

                    }
                }

            }

            override fun onFailure(call: Call<DataResponse1>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

    //획득 화분 정보 가져오기
    fun getUnlockedFpResult(fragment: UnlockedFlowerpotFragment) {
        val userIdx = getUserIdx(fragment)

        dataService.getUnlockedFpResult(userIdx).enqueue(object : Callback<DataResponse2> {
            override fun onResponse(call: Call<DataResponse2>, response: Response<DataResponse2>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        Log.d("resp", resp.result.toString())
                        fragment.setUnlockedFpList(resp.result)
                    }

                    2019 -> {

                    }
                    2020 -> {

                    }
                    else -> {

                    }
                }
            }

            override fun onFailure(call: Call<DataResponse2>, t: Throwable) {
            }

        })

    }


    // 미획득 화분 정보 가져오기
    fun getLockedFpResult(fragment: LockedFlowerpotFragment) {
        val userIdx = getUserIdx(fragment)

        dataService.getLockedFpResult(userIdx).enqueue(object : Callback<DataResponse3> {
            override fun onResponse(call: Call<DataResponse3>, response: Response<DataResponse3>) {
                val resp = response.body()!!
                // 실패 시 코드 작성하기
                when (resp.code) {
                    1000 -> {
                        Log.d("resp", resp.result.toString())
                        fragment.setLockedFpList(resp.result)
                    }
                }
            }

            override fun onFailure(call: Call<DataResponse3>, t: Throwable) {
            }

        })
    }

}