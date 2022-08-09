package com.example.librog.ui.main.history

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.data.HistoryBookData
import com.example.librog.data.remote.history.HistoryResult
import com.example.librog.data.remote.history.HistoryService
import com.example.librog.databinding.FragmentHistoryBinding
import com.example.librog.ui.BaseFragment
import com.example.librog.ui.main.MainActivity


class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {

    //HistoryBookData가 아닌 다른 데이터 클래스로 넣는 과정 필요한가?
    private var historyBookDataList = ArrayList<HistoryBookData>()
    private val historyService = HistoryService
    private lateinit var historyRVAdapter: HistoryRVAdapter
    override fun initAfterBinding() {
        historyService.getCurrentHistory(this)
        initLayout()
        initClickListener()

    }


    private fun initLayout() {
        historyRVAdapter = HistoryRVAdapter(historyBookDataList)
        //리사이클러뷰에 어댑터 연결
        binding.historyBookListRv.adapter = historyRVAdapter
        binding.historyBookListRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )



        historyRVAdapter.setMyItemClickListener(object : HistoryRVAdapter.OnItemClickListener {
            override fun onItemClick(tempHistoryBookData: HistoryBookData) {
                //정렬 배너 띄워져있을 때 클릭x
                if (binding.historySortBanner.visibility == View.VISIBLE)
                    return

                Toast.makeText(activity, "Book Clicked", Toast.LENGTH_SHORT).show()
            }
        })

        binding.historyBookListRv.layoutManager = GridLayoutManager(context, 3)

    }

    //데이터 받아오는 것까지 성공, recycler에 채워넣는 과정
    fun initData(result: ArrayList<HistoryResult>) {
        for (item in result) {
            historyBookDataList.add(HistoryBookData(item.bookImgUrl))
        }
        historyRVAdapter.notifyDataSetChanged()
    }

    private fun initClickListener() {
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

        }
        binding.historyBannerRateTv.setOnClickListener {
            clickSortTv()
            binding.historySelectedSortTv.text = binding.historyBannerRateTv.text
        }
        binding.historyBannerTitleTv.setOnClickListener {
            clickSortTv()
            binding.historySelectedSortTv.text = binding.historyBannerTitleTv.text
        }
    }

    private fun clickSortTv() {
        binding.historySortBanner.visibility = View.INVISIBLE
        binding.historyBannerSelected.visibility = View.INVISIBLE
        (activity as MainActivity).controlBottomNavVisibility()
        binding.historySortBoxIv.isClickable = true
    }



}

