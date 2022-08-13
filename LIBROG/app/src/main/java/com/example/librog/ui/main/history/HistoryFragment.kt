package com.example.librog.ui.main.history

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.librog.ApplicationClass
import com.example.librog.data.entities.BookImgUrl
import com.example.librog.data.remote.history.FilteredHistoryResult
import com.example.librog.data.remote.history.HistoryInterface
import com.example.librog.data.remote.history.HistoryResponse
import com.example.librog.databinding.FragmentHistoryBinding
import com.example.librog.ui.BaseFragment
import com.example.librog.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {

    private var historyBookImgDataList = ArrayList<BookImgUrl>()
    private var readingRecordIdxList = ArrayList<Int>()
    private lateinit var historyRVAdapter: HistoryRVAdapter
    private val historyService = ApplicationClass.retrofit.create(HistoryInterface::class.java)


    override fun initAfterBinding() {
        val userIdx = getUserIdx(this)

        when (binding.historySelectedSortTv.text) {
            binding.historyBannerRecentTv.text -> getHistoryFilteredByRecent(userIdx)
            binding.historyBannerRateTv.text -> getHistorySortedByRate(userIdx)
            binding.historyBannerTitleTv.text -> getHistoryFilteredByTitle(userIdx)
        }

        initLayout()
        initClickListener(userIdx)
    }


    // API 명세서 2.9 유저 전체 독서기록 필터(최근 순) api
    private fun getHistoryFilteredByRecent(userIdx: Int) {

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
                            changeBookDataList(resp.result)
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
    private fun getHistorySortedByRate(userIdx: Int) {
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
                            changeBookDataList(resp.result)
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
    private fun getHistoryFilteredByTitle(userIdx: Int) {
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
                            changeBookDataList(resp.result)
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

    private fun initLayout() {
        historyRVAdapter = HistoryRVAdapter(historyBookImgDataList, readingRecordIdxList)
        //리사이클러뷰에 어댑터 연결
        binding.historyBookListRv.adapter = historyRVAdapter
        binding.historyBookListRv.layoutManager = GridLayoutManager(context, 3)

        historyRVAdapter.setMyItemClickListener(object : HistoryRVAdapter.OnItemClickListener {

            override fun onItemClick(bookImgUrl: BookImgUrl, readingRecordIdx: Int) {
                //정렬 배너 띄워져있을 때 클릭 시 정렬 배너 내려감
                if (binding.historySortBanner.visibility == View.VISIBLE) {
                    clickSortTv()
                    return
                }
                Toast.makeText(activity, "$readingRecordIdx", Toast.LENGTH_SHORT).show()

                val intent = Intent(context, DetailHistoryActivity::class.java)
                intent.putExtra("readingRecordIdx", readingRecordIdx)
                startActivity(intent)
            }

        })

    }


    private fun initClickListener(userIdx: Int) {
        //최상단 이동
        binding.historyMoveTopBtn.setOnClickListener {
            binding.historyBookListRv.smoothScrollToPosition(0)
        }

        //정렬 박스 클릭
        binding.historySortBoxIv.setOnClickListener {
            binding.historySortBanner.visibility = View.VISIBLE
            binding.historyBannerSelected.visibility = View.VISIBLE
            (activity as MainActivity).controlBottomNavVisibility()
            binding.historySortBoxIv.isClickable = false

        }

        //배너 정렬 텍스트 클릭
        binding.historyBannerRecentTv.setOnClickListener {
            clickSortTv()
            binding.historySelectedSortTv.text = binding.historyBannerRecentTv.text
            getHistoryFilteredByRecent(userIdx)

        }
        binding.historyBannerRateTv.setOnClickListener {
            clickSortTv()
            binding.historySelectedSortTv.text = binding.historyBannerRateTv.text
            getHistorySortedByRate(userIdx)

        }
        binding.historyBannerTitleTv.setOnClickListener {
            clickSortTv()
            binding.historySelectedSortTv.text = binding.historyBannerTitleTv.text
            getHistoryFilteredByTitle(userIdx)
        }
    }


    // 독서 기록 화면에서 최근 읽은 책 조회하고 그 결과를 출력
    // 동시에 해당 결과의 readingRecordIdx를 저장하여
    fun changeBookDataList(result: ArrayList<FilteredHistoryResult>) {
        historyBookImgDataList.clear()
        readingRecordIdxList.clear()

        // bookIdx, imgUrl 값이 null인 경우 임시 데이터 반영
        for (item in result) {
            historyBookImgDataList.add(
                BookImgUrl(
                    bookIdx = item.bookIdx,
                    imgUrl = item.bookImgUrl ?: "https://sadad64.shop/images/Acalypha_reptans.png"
                )
            )
            readingRecordIdxList.add(item.readingRecordIdx)
        }

        historyRVAdapter.notifyDataSetChanged()
    }


    private fun clickSortTv() {
        binding.historySortBanner.visibility = View.INVISIBLE
        binding.historyBannerSelected.visibility = View.INVISIBLE
        (activity as MainActivity).controlBottomNavVisibility()
        binding.historySortBoxIv.isClickable = true
    }

    private fun getUserIdx(fragment: Fragment): Int {
        val spf =
            fragment.activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx", -1)
    }


}

