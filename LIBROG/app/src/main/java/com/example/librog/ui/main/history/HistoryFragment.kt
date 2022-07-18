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

class HistoryFragment : Fragment() {
    lateinit var binding: FragmentHistoryBinding
    private var historybookDatas = ArrayList<HistoryBookData>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        initLayout()
        initData()

        binding.historyMoveTopBtn.setOnClickListener {
            binding.historyBookListRv?.smoothScrollToPosition(0)
        }

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
                Toast.makeText(activity,"Book Clicked",Toast.LENGTH_SHORT).show();
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
            add(HistoryBookData(R.drawable.home_item_book1))
            add(HistoryBookData(R.drawable.home_item_book2))
            add(HistoryBookData(R.drawable.home_item_book1))
            add(HistoryBookData(R.drawable.home_item_book2))
            add(HistoryBookData(R.drawable.home_item_book1))
        }
    }
}

