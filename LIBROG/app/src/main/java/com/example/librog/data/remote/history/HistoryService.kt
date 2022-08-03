package com.example.librog.data.remote.history

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.librog.ApplicationClass.Companion.retrofit
import com.example.librog.data.remote.data.DataResponse1
import com.example.librog.data.remote.data.DataResponse2
import com.example.librog.data.remote.data.DataResponse3
import com.example.librog.ui.main.addFlowerpot.LockedFlowerpotFragment
import com.example.librog.ui.main.addFlowerpot.UnlockedFlowerpotFragment
import com.example.librog.ui.main.flowerpot.FlowerpotFragment
import com.example.librog.ui.main.history.HistoryFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object HistoryService {
    private val historyService = retrofit.create(HistoryInterface::class.java)

    private val userIdx = 1

    private fun getUserIdx(fragment: Fragment): Int{
        val spf = fragment.activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx",-1)
    }


    fun getCurrentHistory(fragment: HistoryFragment) {
//        val userIdx = getUserIdx(fragment)
        Log.e("resp", "test")

        historyService.getCurrentHistory(userIdx).enqueue(object : Callback<ArrayList<HistoryResult>> {
            override fun onResponse(call: Call<ArrayList<HistoryResult>>, response: Response<ArrayList<HistoryResult>>) {
                val resp = response.body()!!
                if (resp.isNotEmpty()){
                    for (item in resp){
                        Log.d("resp", item.toString())
                        fragment.initData(resp)
                    }
                }

            }


            override fun onFailure(call: Call<ArrayList<HistoryResult>>, t: Throwable) {
                Log.d("onfailure", t.toString())
            }
        })

    }

}