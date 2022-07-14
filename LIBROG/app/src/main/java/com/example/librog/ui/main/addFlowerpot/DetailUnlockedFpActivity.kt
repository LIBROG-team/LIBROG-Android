package com.example.librog.ui.main.addFlowerpot

import com.example.librog.data.FlowerpotData
import com.example.librog.databinding.ActivityDetailUnlockedFpBinding
import com.example.librog.ui.BaseActivity

class DetailUnlockedFpActivity :
    BaseActivity<ActivityDetailUnlockedFpBinding>(ActivityDetailUnlockedFpBinding::inflate) {


    //test data
    private var fp = FlowerpotData(
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
    )

    override fun initAfterBinding() {
    }

}