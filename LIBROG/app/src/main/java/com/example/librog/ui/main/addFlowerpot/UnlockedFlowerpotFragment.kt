package com.example.librog.ui.main.addFlowerpot

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.librog.ApplicationClass
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.remote.data.DataInterface
import com.example.librog.data.remote.data.DataResponse2
import com.example.librog.data.remote.data.UnlockedFpResult
import com.example.librog.databinding.FragmentUnlockedFlowerpotBinding
import com.example.librog.ui.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnlockedFlowerpotFragment :
    BaseFragment<FragmentUnlockedFlowerpotBinding>(FragmentUnlockedFlowerpotBinding::inflate) {
    private var unlockedFpList = ArrayList<UnlockedFpResult>()
    private val dataService = ApplicationClass.retrofit.create(DataInterface::class.java)

    private lateinit var adapter: UnlockedFlowerpotRVAdapter


    override fun initAfterBinding() {
        val userIdx = getUserIdx()
        unlockedFpList.clear()
        getUnlockedFpResult(userIdx)
        initUnlockedRV()
    }

    private fun setUnlockedFpList(result: ArrayList<UnlockedFpResult>){
        unlockedFpList.addAll(result)
        adapter.notifyDataSetChanged()
    }

    //획득 화분 정보 가져오기
    private fun getUnlockedFpResult(userIdx: Int) {

        dataService.getUnlockedFpResult(userIdx).enqueue(object : Callback<DataResponse2> {
            override fun onResponse(call: Call<DataResponse2>, response: Response<DataResponse2>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        setUnlockedFpList(resp.result)
                    }

                    2019 -> {

                    }
                    2020 -> {

                    }
                    else -> {

                    }
                }
            }

            override fun onFailure(call: Call<DataResponse2>, t: Throwable) {
            }

        })

    }


    private fun initUnlockedRV() {
        adapter = UnlockedFlowerpotRVAdapter(unlockedFpList)
        binding.unlockedFlowerpotRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.unlockedFlowerpotRv.adapter = adapter
        binding.unlockedFlowerpotRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        adapter.setOnItemClickListener(object : UnlockedFlowerpotRVAdapter.OnItemClickListener{
            override fun onItemClick(fp: UnlockedFpResult){
                val intent = Intent(context, DetailUnlockedFpActivity::class.java)
                intent.putExtra("selectedItem", fp.idx)
                startActivity(intent)
            }

        })


    }


    private fun getUserIdx(): Int{
        val spf = activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx",-1)
    }

}