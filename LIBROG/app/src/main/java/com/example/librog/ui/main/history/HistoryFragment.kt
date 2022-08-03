package com.example.librog.ui.main.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.R
import com.example.librog.data.HistoryBookData
import com.example.librog.databinding.FragmentHistoryBinding
import com.example.librog.ui.main.MainActivity




class HistoryFragment : Fragment() {
    lateinit var binding: FragmentHistoryBinding
    private var historybookDatas = ArrayList<HistoryBookData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        initLayout()
        initData()
        initClickListener()

        return binding.root
    }

    private fun initLayout(){
        val historyRVAdapter = HistoryRVAdapter(historybookDatas)
        //리사이클러뷰에 어댑터 연결
        binding.historyBookListRv.adapter = historyRVAdapter
        binding.historyBookListRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)



        historyRVAdapter.setMyItemClickListener(object : HistoryRVAdapter.OnItemClickListener {
            override fun onItemClick(tempHistoryBookData: HistoryBookData) {
                //정렬 배너 띄워져있을 때 클릭x
                if (binding.historySortBanner.visibility == View.VISIBLE)
                    return

                Toast.makeText(activity,"Book Clicked",Toast.LENGTH_SHORT).show()
            }
        })

        binding.historyBookListRv.layoutManager = GridLayoutManager(context,3)

    }


    private fun initData(){
        historybookDatas.apply{
            add(HistoryBookData(R.drawable.home_item_book1))
            add(HistoryBookData(R.drawable.home_item_book2))
            add(HistoryBookData(R.drawable.home_item_book1))
            add(HistoryBookData(R.drawable.home_item_book2))
            add(HistoryBookData(R.drawable.home_item_book1))
            add(HistoryBookData(R.drawable.home_item_book2))
            add(HistoryBookData(R.drawable.home_item_book1))
            add(HistoryBookData(R.drawable.home_item_book2))
            add(HistoryBookData(R.drawable.home_item_book1))
        }
    }

    private fun initClickListener(){
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
            binding.historySelectedSortTv.text=binding.historyBannerRecentTv.text

        }
        binding.historyBannerRateTv.setOnClickListener {
            clickSortTv()
            binding.historySelectedSortTv.text=binding.historyBannerRateTv.text
        }
        binding.historyBannerTitleTv.setOnClickListener {
            clickSortTv()
            binding.historySelectedSortTv.text=binding.historyBannerTitleTv.text
        }
    }

    private fun clickSortTv(){
        binding.historySortBanner.visibility = View.INVISIBLE
        binding.historyBannerSelected.visibility = View.INVISIBLE
        (activity as MainActivity).controlBottomNavVisibility()
        binding.historySortBoxIv.isClickable = true
    }



}

