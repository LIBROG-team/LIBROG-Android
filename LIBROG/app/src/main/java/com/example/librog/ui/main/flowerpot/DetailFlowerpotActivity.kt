package com.example.librog.ui.main.flowerpot


import androidx.recyclerview.widget.GridLayoutManager
import com.example.librog.ApplicationClass
import com.example.librog.R
import com.example.librog.data.DetailTempFlowerpotData
import com.example.librog.databinding.ActivityDetailFlowerpotBinding
import com.example.librog.ui.BaseActivity


class DetailFlowerpotActivity :
    BaseActivity<ActivityDetailFlowerpotBinding>(ActivityDetailFlowerpotBinding::inflate) {

    private var flowerpotBookList = ArrayList<DetailTempFlowerpotData>()
    private var adapter = DetailFlowerpotRVAdapter(flowerpotBookList)

    override fun initAfterBinding() {

        initData()
        initLayout()

    }

    private fun initLayout() {

        binding.detailFlowerpotBookListRv.adapter = adapter
        binding.detailFlowerpotBookListRv.layoutManager = GridLayoutManager(this, 3)
        binding.detailFlowerpotBackBtnIv.setOnClickListener {
            finish()
        }
        adapter.setMyItemClickListener(object : DetailFlowerpotRVAdapter.OnItemClickListener {
            override fun onItemClick(tempFlowerpotData: DetailTempFlowerpotData) {
                showToast("Book Clicked")
            }
        })
    }

    private fun initData() {
        flowerpotBookList.addAll(
            arrayListOf(
                DetailTempFlowerpotData(R.drawable.home_item_book1),
                DetailTempFlowerpotData(R.drawable.home_item_book2),
                DetailTempFlowerpotData(R.drawable.home_item_book1),
                DetailTempFlowerpotData(R.drawable.home_item_book2),
                DetailTempFlowerpotData(R.drawable.home_item_book1),
                DetailTempFlowerpotData(R.drawable.home_item_book2),
                DetailTempFlowerpotData(R.drawable.home_item_book1),
            )
        )
    }
}