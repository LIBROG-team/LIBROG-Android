package com.example.librog.ui.main.flowerpot

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.R
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.entities.Flowerpot
import com.example.librog.data.remote.data.DataService
import com.example.librog.data.remote.data.FpResult
import com.example.librog.databinding.FragmentFlowerpotBinding
import com.example.librog.ui.BaseFragment
import com.google.gson.Gson

class FlowerpotFragment :
    BaseFragment<FragmentFlowerpotBinding>(FragmentFlowerpotBinding::inflate) {

    private var flowerDataList = ArrayList<FlowerData>()
    private var flowerpotList = ArrayList<Flowerpot>()
    private val dataService = DataService
    private lateinit var adapter: FlowerpotRVAdapter

    override fun initAfterBinding() {
        flowerDataList.clear()
        initFlowerpotData()

        adapter = FlowerpotRVAdapter(flowerDataList, flowerpotList)
        binding.flowerpotListRv.adapter = adapter
        binding.flowerpotListRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.flowerpotTotalTv.text =
            String.format(getString(R.string.flowerpot_total), flowerDataList.size)
        adapter.setMyItemClickListener(object : FlowerpotRVAdapter.OnItemClickListener {
            override fun onItemClick(flowerData: FlowerData, flowerpot: Flowerpot) {
                val intent = Intent(context, DetailFlowerpotActivity::class.java)
                val gson = Gson()
                val fdJson = gson.toJson(flowerData)
                val fpJson = gson.toJson(flowerpot)
                intent.putExtra("flowerData", fdJson)
                intent.putExtra("flowerpot", fpJson)
                startActivity(intent)
            }
        })

    }

    private fun initFlowerpotData() {
        dataService.getFpList(this)
    }


    fun setData(result: ArrayList<FpResult>){

        val tempFd = ArrayList<FlowerData>()
        val tempFp = ArrayList<Flowerpot>()

        for (item in result) {
            tempFd.add(
                FlowerData(
                    item.flowerDataIdx,
                    item.name,
                    item.engName,
                    item.flowerImgUrl,
                    item.flowerImgUrl,
                    item.maxExp,
                    item.bloomingPeriod,
                    item.content,
                    item.type,
                    "active"

                )
            )
            tempFp.add(
                Flowerpot(
                    item.flowerPotIdx,
                    getUserIdx(),
                    item.flowerDataIdx,
                    item.startDate.slice(IntRange(0,9)),
                    item.lastDate.slice(IntRange(0,9)),
                    item.exp,
                    item.recordCount,
                    "active"
                )
            )

            loadFdData(tempFd)
            loadFpData(tempFp)
        }
    }

    private fun loadFpData(data: ArrayList<Flowerpot>){
        adapter.setFpList(data)
        adapter.notifyDataSetChanged()
    }

    private fun loadFdData(data: ArrayList<FlowerData>){
        adapter.setFdList(data)
        adapter.notifyDataSetChanged()
    }

    fun getUserIdx(): Int{
        return 1
    }

}