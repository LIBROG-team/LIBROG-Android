package com.example.librog.ui.main.addFlowerpot

import android.content.Intent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.entities.Flowerpot
import com.example.librog.databinding.FragmentUnlockedFlowerpotBinding
import com.example.librog.ui.BaseFragment

class UnlockedFlowerpotFragment :
    BaseFragment<FragmentUnlockedFlowerpotBinding>(FragmentUnlockedFlowerpotBinding::inflate) {
    override fun initAfterBinding() {
        initUnlockedRV()

    }

    private fun initUnlockedRV() {
        val adapter = UnlockedFlowerpotRVAdapter()
        binding.unlockedFlowerpotRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.unlockedFlowerpotRv.adapter = adapter
        binding.unlockedFlowerpotRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        adapter.setOnItemClickListener(object : UnlockedFlowerpotRVAdapter.OnItemClickListener{
            override fun onItemClick(flowerpot: FlowerData){
                val intent = Intent(context, DetailUnlockedFpActivity::class.java)
                intent.putExtra("selectedItem", flowerpot.idx)
                startActivity(intent)
            }

        })

        adapter.addUnlockedFlowerpot(
            arrayListOf(

            )
        )

    }


}