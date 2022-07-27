package com.example.librog.ui.main.addFlowerpot

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.remote.data.DataService
import com.example.librog.data.remote.data.UnlockedFpResult
import com.example.librog.databinding.FragmentUnlockedFlowerpotBinding
import com.example.librog.ui.BaseFragment

class UnlockedFlowerpotFragment :
    BaseFragment<FragmentUnlockedFlowerpotBinding>(FragmentUnlockedFlowerpotBinding::inflate) {
    private var unlockedFpList = ArrayList<UnlockedFpResult>()
    private val dataService = DataService
    private lateinit var adapter: UnlockedFlowerpotRVAdapter


    override fun initAfterBinding() {
        unlockedFpList.clear()
        dataService.getUnlockedFpResult(this)
        initUnlockedRV()
    }

    fun setUnlockedFpList(result: ArrayList<UnlockedFpResult>){
        unlockedFpList.addAll(result)
        adapter.notifyDataSetChanged()
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


}