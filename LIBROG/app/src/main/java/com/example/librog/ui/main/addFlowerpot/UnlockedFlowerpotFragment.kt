package com.example.librog.ui.main.addFlowerpot

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librog.data.FlowerpotData
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

        adapter.addUnlockedFlowerpot(
            arrayListOf(
                FlowerpotData(
                    "라넌큘러스",
                    "Ranunculus",
                    "2022.05.07",
                    "2022.05.18",
                    "https://cdn.shopify.com/s/files/1/1419/7120/products/Ranunculus_Pink.SHUT.SQ_1024x.jpg?v=1580943589",
                    "고전",
                    3,
                    3600,
                    10000,
                    "temp",
                    "temp",
                    "temp"
                ),
                FlowerpotData(
                    "튤립",
                    "Tulip",
                    "2022.05.07",
                    "2022.05.18",
                    "https://treethink.kr/prod_img/202110/20211016022328_616a61d0c3ffe.jpg",
                    "SF",
                    3,
                    9000,
                    10000,
                    "temp",
                    "temp",
                    "temp"
                ),

            )
        )

    }


}