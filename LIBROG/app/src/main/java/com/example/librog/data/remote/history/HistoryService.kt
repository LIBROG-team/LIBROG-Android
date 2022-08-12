package com.example.librog.data.remote.history

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.librog.ApplicationClass.Companion.retrofit
import com.example.librog.ui.main.addbook.AddBookSelectActivity
import com.example.librog.ui.main.flowerpot.DetailFlowerpotActivity
import com.example.librog.ui.main.history.HistoryFragment
import com.example.librog.ui.main.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object HistoryService {
    private val historyService = retrofit.create(HistoryInterface::class.java)


    private fun getUserIdx(fragment: Fragment): Int {
        val spf =
            fragment.activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx", -1)
    }


    // API 명세서 2.2 화분별 독서 기록 조회 API
    fun getFlowerpotBookRecord(activity: DetailFlowerpotActivity, idx: Int) {
        historyService.getFlowerpotBookRecord(idx).enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        Log.d("resp", resp.result.toString())
                        activity.initData(resp.result)
                    }
                    3006 -> {
                        activity.noBookInFlowerpot()
                    }
                    else -> {
                        Log.d("resp", "${resp.code} + resp.message")

                    }
                }
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {

            }

        })
    }


    // 2.5 독서기록 추가 api
    fun addUserBookRecord(activity: AddBookSelectActivity, userBookRecord: UserBookRecord){
        historyService.addUserBookRecord(userBookRecord).enqueue(object : Callback<AddBookResponse>{
            override fun onResponse(call: Call<AddBookResponse>, response: Response<AddBookResponse>) {
                val resp = response.body()!!
                Log.d("AddBook/SUCCESS", resp.toString())
                when(resp.code){
                    1000 ->{
                        Log.d("AddBook/SUCCESS" ,resp.toString())
                        activity.readingRecord.bookIdx = resp.result.createdRecordId!!
                    }
                    2025 ->{
                        activity.showToast(resp.message)
                    }
                    3005 ->{
                        activity.showToast(resp.message)
                    }
                    3007 ->{
                        activity.showToast(resp.message)
                    }
                    else ->{
                        activity.showToast(resp.message)
                    }
                }

            }

            override fun onFailure(call: Call<AddBookResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }











    //  API 명세서 2.7 최근 읽은 책 조회 API
    fun getRecentBookRecord(fragment: HomeFragment) {
        val userIdx = getUserIdx(fragment)

        historyService.getRecentBookRecord(userIdx)
            .enqueue(object : Callback<ArrayList<HistoryResult>> {
                override fun onResponse(
                    call: Call<ArrayList<HistoryResult>>,
                    response: Response<ArrayList<HistoryResult>>
                ) {
                    val resp = response.body()!!
                    if (resp.isNotEmpty()) {
                        for (item in resp) {
                            Log.d("resp", item.toString())
                        }
                    }

                }

                override fun onFailure(call: Call<ArrayList<HistoryResult>>, t: Throwable) {
                    Log.d("onfailure", t.toString())
                }
            })

    }

    // API 명세서 2.9 유저 전체 독서기록 필터(최근 순) api
    fun getHistoryFilteredByRecent(fragment: HistoryFragment) {
        val userIdx = getUserIdx(fragment)

        historyService.getHistoryFilteredByRecent(userIdx)
            .enqueue(object : Callback<HistoryResponse> {
                override fun onResponse(
                    call: Call<HistoryResponse>,
                    response: Response<HistoryResponse>
                ) {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> {
                            Log.d("resp", resp.result.toString())
                            fragment.changeBookDataList(resp.result)
                        }
                        else -> {
                            Log.e("resp", resp.message)
                        }
                    }
                }

                override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                }
            })
    }

    // API 명세서 2.10 유저 전체 독서기록 필터(별점 순) api
    fun getHistorySortedByRate(fragment: HistoryFragment) {
        val userIdx = getUserIdx(fragment)

        historyService.getHistoryFilteredByRate(userIdx)
            .enqueue(object : Callback<HistoryResponse> {
                override fun onResponse(
                    call: Call<HistoryResponse>,
                    response: Response<HistoryResponse>
                ) {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> {
                            Log.d("resp", resp.result.toString())
                            fragment.changeBookDataList(resp.result)
                        }
                        else -> {
                            Log.e("resp", resp.message)
                        }
                    }
                }

                override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                }

            })
    }

    // API 명세서 2.11 전체 독서기록 필터(제목 순) api
    fun getHistoryFilteredByTitle(fragment: HistoryFragment) {
        val userIdx = getUserIdx(fragment)

        historyService.getHistoryFilteredByTitle(userIdx)
            .enqueue(object : Callback<HistoryResponse> {
                override fun onResponse(
                    call: Call<HistoryResponse>,
                    response: Response<HistoryResponse>
                ) {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> {
                            Log.d("resp", resp.result.toString())
                            fragment.changeBookDataList(resp.result)
                        }
                        else -> {
                            Log.e("resp", resp.message)
                        }
                    }
                }

                override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                }
            })
    }
}

