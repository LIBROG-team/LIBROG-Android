package com.example.librog.ui.main.history

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.data.HistoryBookData
import com.example.librog.data.entities.BookImgUrl
import com.example.librog.data.remote.history.FilteredHistoryResult
import com.example.librog.data.remote.history.HistoryResult
import com.example.librog.data.remote.history.HistoryService
import com.example.librog.databinding.FragmentHistoryBinding
import com.example.librog.ui.BaseFragment
import com.example.librog.ui.main.MainActivity


class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {

    private var historyBookDataList = ArrayList<BookImgUrl>()
    private val historyService = HistoryService
    private lateinit var historyRVAdapter: HistoryRVAdapter
    override fun initAfterBinding() {
        historyService.getHistoryFilteredByRecent(this)
        initLayout()
        initClickListener()

    }


    private fun initLayout() {
        historyRVAdapter = HistoryRVAdapter(historyBookDataList)
        //리사이클러뷰에 어댑터 연결
        binding.historyBookListRv.adapter = historyRVAdapter
        binding.historyBookListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        historyRVAdapter.setMyItemClickListener(object : HistoryRVAdapter.OnItemClickListener {
            override fun onItemClick(bookImgUrl: BookImgUrl) {
                //정렬 배너 띄워져있을 때 클릭 시 정렬 배너 내려감
                if (binding.historySortBanner.visibility == View.VISIBLE){
                    clickSortTv()
                    return
                }
                Toast.makeText(activity, "Book Clicked", Toast.LENGTH_SHORT).show()
            }
        })

        binding.historyBookListRv.layoutManager = GridLayoutManager(context, 3)

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
            historyService.getHistoryFilteredByRecent(this)

        }
        binding.historyBannerRateTv.setOnClickListener {
            clickSortTv()
            binding.historySelectedSortTv.text = binding.historyBannerRateTv.text
            historyService.getHistorySortedByRate(this)

        }
        binding.historyBannerTitleTv.setOnClickListener {
            clickSortTv()
            binding.historySelectedSortTv.text = binding.historyBannerTitleTv.text
            historyService.getHistoryFilteredByTitle(this)
        }
    }


    //homeFragment에 작성되어야 하는 코드. api에서 제공하는 데이터 중 bookIdx가 없어서 임시로 주석 처리
    fun getRecentBookRecord(result: ArrayList<HistoryResult>) {
//        historyBookDataList.clear()
//        for (item in result) {
//            historyBookDataList.add(BookImgUrl(bookIdx = , imgUrl = item.bookImgUrl?:"no image"))
//        }
//        historyRVAdapter.notifyDataSetChanged()
    }


    fun changeBookDataList(result: ArrayList<FilteredHistoryResult>) {
        historyBookDataList.clear()
        for (item in result){
            historyBookDataList.add(
                // bookIdx, imgUrl 값이 null인 경우 임시 데이터 반영
                // 현재 더미 데이터 "bookimg.url" 이라서 앱 실행 시 화면에는 나타나지 않음
                BookImgUrl(bookIdx = item.bookIdx?:1, imgUrl = item.bookImgUrl?:"https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F345555%3Ftimestamp%3D20220726170305")
            )
        }
        historyRVAdapter.notifyDataSetChanged()
    }



    private fun clickSortTv() {
        binding.historySortBanner.visibility = View.INVISIBLE
        binding.historyBannerSelected.visibility = View.INVISIBLE
        (activity as MainActivity).controlBottomNavVisibility()
        binding.historySortBoxIv.isClickable = true
    }



}

