package com.example.librog.ui.main.flowerpot

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.R
import com.example.librog.data.FlowerpotData
import com.example.librog.databinding.FragmentFlowerpotBinding
import com.example.librog.ui.BaseFragment
import com.google.gson.Gson

class FlowerpotFragment :
    BaseFragment<FragmentFlowerpotBinding>(FragmentFlowerpotBinding::inflate) {

    private var flowerpotDataList = ArrayList<FlowerpotData>()

    override fun initAfterBinding() {
        flowerpotDataList.clear()
        initFlowerpotData()

        val adapter = FlowerpotRVAdapter(flowerpotDataList)
        binding.flowerpotListRv.adapter = adapter
        binding.flowerpotListRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.flowerpotTotalTv.text =
            String.format(getString(R.string.flowerpot_total), flowerpotDataList.size)
        adapter.setMyItemClickListener(object : FlowerpotRVAdapter.OnItemClickListener {
            override fun onItemClick(flowerpotData: FlowerpotData) {
                val intent = Intent(context, DetailFlowerpotActivity::class.java)
                val gson = Gson()
                val fpJson = gson.toJson(flowerpotData)
                intent.putExtra("flowerpot", fpJson)
                startActivity(intent)
            }
        })

    }

    private fun initFlowerpotData() {
        val testList = arrayListOf<FlowerpotData>(
            FlowerpotData(
                "라넌큘러스",
                "Ranunculus",
                "2022.05.07",
                "2022.05.18",
                "https://cdn.shopify.com/s/files/1/1419/7120/products/Ranunculus_Pink.SHUT.SQ_1024x.jpg?v=1580943589",
                "고전",
                3,
                3600,
                10000,
                "temp",
                "temp",
                "temp"
            ),
            FlowerpotData(
                "튤립",
                "Tulip",
                "2022.05.07",
                "2022.05.18",
                "https://treethink.kr/prod_img/202110/20211016022328_616a61d0c3ffe.jpg",
                "SF",
                3,
                9000,
                10000,
                "temp",
                "temp",
                "temp"
            ),
            FlowerpotData(
                "해바라기",
                "Sunflower",
                "2022.05.07",
                "2022.05.18",
                "https://treethink.kr/prod_img/202110/20211016022328_616a61d0c3ffe.jpg",
                "SF",
                3,
                9000,
                10000,
                "temp",
                "temp",
                "temp"
            )
        )
        flowerpotDataList.addAll(testList)
    }

}