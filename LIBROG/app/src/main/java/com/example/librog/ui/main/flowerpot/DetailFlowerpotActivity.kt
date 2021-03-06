package com.example.librog.ui.main.flowerpot


import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.librog.R
import com.example.librog.data.DetailTempFlowerpotData
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.entities.Flowerpot
import com.example.librog.databinding.ActivityDetailFlowerpotBinding
import com.example.librog.ui.BaseActivity
import com.example.librog.ui.main.addFlowerpot.AddFlowerpotActivity
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
        val curFdJson = intent.getStringExtra("flowerData")
        val curFd = gson.fromJson(curFdJson, FlowerData::class.java)
        val curFpJson = intent.getStringExtra("flowerpot")
        val curFp = gson.fromJson(curFpJson, Flowerpot::class.java)


        binding.apply {
            detailFlowerpotNameTv.text = curFd.name

            Glide.with(applicationContext)
                .load(curFd.flowerpotImgUrl)
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
                applicationContext.getString(R.string.detail_flowerpot_exp_text), curFd.name
            )

            detailFlowerpotExpNumTv.text = String.format(
                applicationContext.getString(R.string.detail_flowerpot_exp_num),
                curFp.exp,
                curFd.maxExp
            )
            val progress = (curFp.exp / curFd.maxExp.toFloat()) * 100

            detailFlowerpotProgressPb.progress = progress.toInt()

        }

        binding.detailFlowerpotBookListRv.adapter = adapter
        binding.detailFlowerpotBookListRv.layoutManager = GridLayoutManager(this, 3)
        binding.detailFlowerpotBackBtnIv.setOnClickListener {
            finish()
        }

        //????????? ????????? ??? ????????? ?????? ????????? Toast ?????????
        adapter.setMyItemClickListener(object : DetailFlowerpotRVAdapter.OnItemClickListener {
            override fun onItemClick(tempFlowerpotData: DetailTempFlowerpotData) {
                showToast("Book Clicked")
            }
        })

        //?????? ?????? ???????????? ?????? ???????????? ??? ?????? ?????? ???????????? ???????????? ????????? ?????????. ?????? ?????? ??????
        binding.detailFlowerpotListBtnIv.setOnClickListener {
            startActivity(Intent(this, AddFlowerpotActivity::class.java))
        }
    }



    //????????? ????????? ??? ????????? ????????? ??????
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