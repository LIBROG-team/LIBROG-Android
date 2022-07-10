package com.example.librog.ui.main.flowerpot

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.ApplicationClass
import com.example.librog.R
import com.example.librog.data.FlowerpotData
import com.example.librog.databinding.FragmentFlowerpotBinding
import com.example.librog.ui.BaseFragment

class FlowerpotFragment : BaseFragment<FragmentFlowerpotBinding>(FragmentFlowerpotBinding::inflate) {

    private var flowerpotDataList = ArrayList<FlowerpotData>()

    override fun initAfterBinding() {
        initFlowerpotData()

        val adapter = FlowerpotRVAdapter(flowerpotDataList)
        binding.flowerpotListRv.adapter = adapter
        binding.flowerpotListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.flowerpotTotalTv.text = String.format(getString(R.string.flowerpot_total),flowerpotDataList.size)
        adapter.setMyItemClickListener(object : FlowerpotRVAdapter.OnItemClickListener{
            override fun onItemClick(flowerpotData: FlowerpotData) {
                startActivity(Intent(context, DetailFlowerpotActivity::class.java))
            }
        })

    }

    private fun initFlowerpotData() {
        val testList = arrayListOf<FlowerpotData>(
            FlowerpotData("라넌큘러스", "2022.05.07", "2022.05.18", 3, "고전"),
            FlowerpotData("튤립", "2022.03.11", "2022.05.07", 11, "미스터리"),
            FlowerpotData("해바라기", "2022.2.21", "2022.03.11", 4, "SF"),
            FlowerpotData("무궁화", "2022.05.07", "2022.05.18", 3, "고전")
        )
        flowerpotDataList.addAll(testList)
    }

}