package com.example.librog.ui.main.flowerpot

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.R
import com.example.librog.data.entities.FlowerData
import com.example.librog.databinding.FragmentFlowerpotBinding
import com.example.librog.ui.BaseFragment
import com.google.gson.Gson

class FlowerpotFragment :
    BaseFragment<FragmentFlowerpotBinding>(FragmentFlowerpotBinding::inflate) {

    private var flowerDataList = ArrayList<FlowerData>()

    override fun initAfterBinding() {
        flowerDataList.clear()
        initFlowerpotData()

        val adapter = FlowerpotRVAdapter(flowerDataList)
        binding.flowerpotListRv.adapter = adapter
        binding.flowerpotListRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.flowerpotTotalTv.text =
            String.format(getString(R.string.flowerpot_total), flowerDataList.size)
        adapter.setMyItemClickListener(object : FlowerpotRVAdapter.OnItemClickListener {
            override fun onItemClick(flowerData: FlowerData) {
                val intent = Intent(context, DetailFlowerpotActivity::class.java)
                val gson = Gson()
                val fpJson = gson.toJson(flowerData)
                intent.putExtra("flowerpot", fpJson)
                startActivity(intent)
            }
        })

    }

    private fun initFlowerpotData() {

    }

}