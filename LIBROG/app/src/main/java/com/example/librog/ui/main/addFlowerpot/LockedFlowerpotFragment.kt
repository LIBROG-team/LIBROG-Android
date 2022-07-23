package com.example.librog.ui.main.addFlowerpot

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.remote.data.DataService
import com.example.librog.data.remote.data.LockedFpResult
import com.example.librog.databinding.FragmentLockedFlowerpotBinding
import com.example.librog.ui.BaseFragment
import kotlin.collections.ArrayList

class LockedFlowerpotFragment :
    BaseFragment<FragmentLockedFlowerpotBinding>(FragmentLockedFlowerpotBinding::inflate) {

    private var lockedFpList = ArrayList<LockedFpResult>()
    private val dataService = DataService
    private lateinit var adapter: LockedFlowerpotRVAdapter

    override fun initAfterBinding() {
        lockedFpList.clear()
        dataService.getLockedFpResult(this)
        initLockedRv()
    }

    private fun initLockedRv() {
        adapter = LockedFlowerpotRVAdapter(lockedFpList)
        binding.lockedFlowerpotRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.lockedFlowerpotRv.adapter = adapter
        binding.lockedFlowerpotRv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    fun setLockedFpList(result: ArrayList<LockedFpResult>) {
        lockedFpList.addAll(result)
        adapter.notifyDataSetChanged()
    }

}