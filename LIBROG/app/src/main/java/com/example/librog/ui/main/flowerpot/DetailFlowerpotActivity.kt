package com.example.librog.ui.main.flowerpot


import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.librog.ApplicationClass
import com.example.librog.R
import com.example.librog.data.DetailTempFlowerpotData
import com.example.librog.data.FlowerpotData
import com.example.librog.databinding.ActivityDetailFlowerpotBinding
import com.example.librog.ui.BaseActivity
import com.google.gson.Gson


class DetailFlowerpotActivity :
    BaseActivity<ActivityDetailFlowerpotBinding>(ActivityDetailFlowerpotBinding::inflate) {

    private var flowerpotBookList = ArrayList<DetailTempFlowerpotData>()
    private var adapter = DetailFlowerpotRVAdapter(flowerpotBookList)
    private var gson = Gson()

    override fun initAfterBinding() {
        initData()
        initLayout()

    }

    private fun initLayout() {
        val curFpJson = intent.getStringExtra("flowerpot")
        val curFp = gson.fromJson(curFpJson, FlowerpotData::class.java)

        binding.apply {
            detailFlowerpotNameTv.text = curFp.name

            Glide.with(applicationContext)
                .load(curFp.flowerpotImgUrl)
                .into(detailFlowerpotFlowerimgIv)

            detailFlowerpotDateTv.text = String.format(
                applicationContext.getString(R.string.flowerpot_date),
                curFp.startDate,
                curFp.lastDate
            )
            detailFlowerpotRecordCountTv.text = String.format(
                applicationContext.getString(R.string.flowerpot_record), curFp.recordCount
            )

            detailFlowerpotExpTextTv.text = String.format(
                applicationContext.getString(R.string.detail_flowerpot_exp_text), curFp.name
            )

            detailFlowerpotExpNumTv.text = String.format(
                applicationContext.getString(R.string.detail_flowerpot_exp_num),
                curFp.exp,
                curFp.maxExp
            )
            val progress = (curFp.exp / curFp.maxExp.toFloat()) * 100

            detailFlowerpotProgressPb.progress = progress.toInt()

        }

        binding.detailFlowerpotBookListRv.adapter = adapter
        binding.detailFlowerpotBookListRv.layoutManager = GridLayoutManager(this, 3)
        binding.detailFlowerpotBackBtnIv.setOnClickListener {
            finish()
        }
        adapter.setMyItemClickListener(object : DetailFlowerpotRVAdapter.OnItemClickListener {
            override fun onItemClick(tempFlowerpotData: DetailTempFlowerpotData) {
                showToast("Book Clicked")
            }
        })
    }

    private fun initData() {

        flowerpotBookList.addAll(
            arrayListOf(
                DetailTempFlowerpotData(R.drawable.home_item_book1),
                DetailTempFlowerpotData(R.drawable.home_item_book2),
                DetailTempFlowerpotData(R.drawable.home_item_book1),
                DetailTempFlowerpotData(R.drawable.home_item_book2),
                DetailTempFlowerpotData(R.drawable.home_item_book1),
                DetailTempFlowerpotData(R.drawable.home_item_book2),
                DetailTempFlowerpotData(R.drawable.home_item_book1),
            )
        )
    }
}