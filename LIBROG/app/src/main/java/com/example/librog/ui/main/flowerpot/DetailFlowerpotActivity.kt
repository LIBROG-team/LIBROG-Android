package com.example.librog.ui.main.flowerpot


import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.librog.R
import com.example.librog.data.DetailTempFlowerpotData
import com.example.librog.data.entities.FlowerData
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
        val curFpJson = intent.getStringExtra("flowerpot")
        val curFp = gson.fromJson(curFpJson, FlowerData::class.java)

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

        //화분에 기록된 책 클릭한 경우 임시로 Toast 메시지
        adapter.setMyItemClickListener(object : DetailFlowerpotRVAdapter.OnItemClickListener {
            override fun onItemClick(tempFlowerpotData: DetailTempFlowerpotData) {
                showToast("Book Clicked")
            }
        })

        //화분 상세 화면에서 버튼 클릭했을 시 화분 추가 액티비티 나오도록 임시로 설계함. 추후 수정 예정
        binding.detailFlowerpotListBtnIv.setOnClickListener {
            startActivity(Intent(this, AddFlowerpotActivity::class.java))
        }
    }



    //화분에 기록된 책 임시로 데이터 삽입
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