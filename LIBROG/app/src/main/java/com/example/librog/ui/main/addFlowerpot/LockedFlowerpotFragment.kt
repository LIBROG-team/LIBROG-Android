package com.example.librog.ui.main.addFlowerpot

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.data.entities.FlowerData
import com.example.librog.databinding.FragmentLockedFlowerpotBinding
import com.example.librog.ui.BaseFragment

class LockedFlowerpotFragment : BaseFragment<FragmentLockedFlowerpotBinding>(FragmentLockedFlowerpotBinding::inflate) {
    override fun initAfterBinding() {
        initLockedRv()
    }

    private fun initLockedRv() {
        val adapter = LockedFlowerpotRVAdapter()
        binding.lockedFlowerpotRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.lockedFlowerpotRv.adapter = adapter
        binding.lockedFlowerpotRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

}