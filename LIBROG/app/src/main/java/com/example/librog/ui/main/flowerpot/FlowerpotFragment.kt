package com.example.librog.ui.main.flowerpot

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.ApplicationClass
import com.example.librog.R
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.entities.Flowerpot
import com.example.librog.data.remote.data.DataInterface
import com.example.librog.data.remote.data.DataResponse1
import com.example.librog.data.remote.data.FpResult
import com.example.librog.databinding.FragmentFlowerpotBinding
import com.example.librog.ui.BaseFragment
import com.example.librog.ui.main.addFlowerpot.AddFlowerpotActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlowerpotFragment :
    BaseFragment<FragmentFlowerpotBinding>(FragmentFlowerpotBinding::inflate) {

    private var flowerDataList = ArrayList<FlowerData>()
    private var flowerpotList = ArrayList<Flowerpot>()
    private val dataService = ApplicationClass.retrofit.create(DataInterface::class.java)
    private lateinit var adapter: FlowerpotRVAdapter

    override fun initAfterBinding() {
        val userIdx = getUserIdx()
        flowerDataList.clear()
        flowerpotList.clear()
        getFpList(userIdx)


        adapter = FlowerpotRVAdapter(flowerDataList, flowerpotList)
        binding.flowerpotListRv.adapter = adapter
        binding.flowerpotListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.flowerpotListRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.flowerpotTotalTv.text = String.format(getString(R.string.flowerpot_total), flowerpotList.size)

        initClickListener()

    }

    private fun initClickListener(){
        adapter.setMyItemClickListener(object : FlowerpotRVAdapter.OnItemClickListener {
            override fun onItemClick(flowerData: FlowerData, flowerpot: Flowerpot) {
                val intent = Intent(context, DetailFlowerpotActivity::class.java)
                val gson = Gson()
                val fdJson = gson.toJson(flowerData)
                val fpJson = gson.toJson(flowerpot)
                intent.putExtra("flowerData", fdJson)
                intent.putExtra("flowerpot", fpJson)
                intent.putExtra("flowerpotIdx", flowerpot.idx)
                startActivity(intent)
            }
        })


        binding.flowerpotAddflowerpotBtn.setOnClickListener {
            val intent = Intent(context, AddFlowerpotActivity::class.java)
            intent.putExtra("title", "화분추가")
            startActivity(intent)
        }
    }

    //화분 정보 가져오기
    private fun getFpList(userIdx: Int) {
        dataService.getFpList(userIdx).enqueue(object : Callback<DataResponse1> {
            override fun onResponse(call: Call<DataResponse1>, response: Response<DataResponse1>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        Log.d("resp", resp.result.size.toString())
                        setData(resp.result)
                    }

                    2019 -> {

                    }
                    2020 -> {

                    }
                    else -> {

                    }
                }

            }

            override fun onFailure(call: Call<DataResponse1>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

    // 백엔드 api에서 받은 result 결과를 FlowerData, Flowerpot 형식에 맞게 추가하고 recyclerview에 적용
    fun setData(result: ArrayList<FpResult>){
        for (item in result) {
            if (item.flowerDataIdx != null){
                flowerDataList.add(
                    FlowerData(
                        item.flowerDataIdx!!,
                        item.name!!,
                        item.engName!!,
                        item.flowerImgUrl?: "",
                        item.flowerImgUrl?:"",
                        item.maxExp!!,
                        item.bloomingPeriod!!,
                        item.content ?: "",
                        item.type?:"",
                        "active"

                    )
                )
                flowerpotList.add(
                    Flowerpot(
                        item.flowerPotIdx!!,
                        getUserIdx(),
                        item.flowerDataIdx!!,
                        item.startDate!!.slice(IntRange(0,9)),
                        item.lastDate!!.slice(IntRange(0,9)),
                        item.exp!!,
                        item.recordCount!!,
                        "active"
                    )
                )

                binding.flowerpotTotalTv.text =
                    String.format(getString(R.string.flowerpot_total), flowerpotList.size)

                adapter.notifyDataSetChanged()

            } else {
                // 유저에 화분이 존재하지 않는 경우
//                binding.flowerpotListRv.visibility = View.GONE
            }


        }
    }


    private fun getUserIdx(): Int{
        val spf = activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx",-1)
    }




}