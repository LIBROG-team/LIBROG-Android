package com.example.librog.ui.main.flowerpot

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.R
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.entities.Flowerpot
import com.example.librog.data.local.AppDatabase
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
    private lateinit var db: AppDatabase
    private var fpCnt: Int = 0

    override fun initAfterBinding() {
        flowerDataList.clear()
        flowerpotList.clear()
        db = AppDatabase.getInstance(requireContext())!!
        dataService.getFpList(this)

        adapter = FlowerpotRVAdapter(flowerDataList, flowerpotList)
        binding.flowerpotListRv.adapter = adapter
        binding.flowerpotListRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.flowerpotTotalTv.text =
            String.format(getString(R.string.flowerpot_total), flowerpotList.size)

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

    // getFpList() 실행 시 실행되는 코드
    // 백엔드 api에서 받은 result 결과를 FlowerData, Flowerpot 형식에 맞게 추가하고 recyclerview에 적용
    fun setData(result: ArrayList<FpResult>){
        for (item in result) {
            flowerDataList.add(
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
            flowerpotList.add(
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

            binding.flowerpotTotalTv.text =
                String.format(getString(R.string.flowerpot_total), flowerpotList.size)
            adapter.notifyDataSetChanged()

            //이후 비동기 방식으로 room db에 추가할 수 있음
        }
    }


    // room db 구축되면 user Id 조회하는 기능 필요
    fun getUserIdx(): Int{
        return 1
    }

}