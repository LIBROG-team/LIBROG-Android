package com.example.librog.ui.main.flowerpot


import android.content.Intent
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.librog.ApplicationClass
import com.example.librog.R
import com.example.librog.data.entities.BookImgUrl
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.entities.Flowerpot
import com.example.librog.data.remote.history.FilteredHistoryResult
import com.example.librog.data.remote.history.HistoryInterface
import com.example.librog.data.remote.history.HistoryResponse
import com.example.librog.databinding.ActivityDetailFlowerpotBinding
import com.example.librog.ui.BaseActivity
import com.example.librog.ui.main.addFlowerpot.AddFlowerpotActivity
import com.example.librog.ui.main.history.DetailHistoryActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailFlowerpotActivity :
    BaseActivity<ActivityDetailFlowerpotBinding>(ActivityDetailFlowerpotBinding::inflate) {

    private var flowerpotBookList = ArrayList<BookImgUrl>()
    private var readingRecordIdxList = ArrayList<Int>()
    private lateinit var adapter:DetailFlowerpotRVAdapter
    private var gson = Gson()
    private val historyService = ApplicationClass.retrofit.create(HistoryInterface::class.java)


    override fun initAfterBinding() {
        val idx = intent.getIntExtra("flowerpotIdx", 1)
        getFlowerpotBookRecord(idx)
        initLayout()
    }

    // API 명세서 2.2 화분별 독서 기록 조회 API
    private fun getFlowerpotBookRecord(idx: Int) {
        historyService.getFlowerpotBookRecord(idx).enqueue(object :
            Callback<HistoryResponse> {
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        Log.d("resp", resp.result.toString())
                        initData(resp.result)
                    }
                    3006 -> {
                        hideFlowerpotRV()
                    }
                    else -> {
                        Log.d("resp", "${resp.code} + resp.message")

                    }
                }
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {

            }

        })
    }


    fun hideFlowerpotRV(){
        binding.detailFlowerpotBookListRv.visibility = View.GONE
        binding.detailFlowerpotNoBookTv.visibility = View.VISIBLE
    }


    fun initData(result: ArrayList<FilteredHistoryResult>) {
        flowerpotBookList.clear()
        readingRecordIdxList.clear()

        binding.detailFlowerpotNoBookTv.visibility = View.GONE
        binding.detailFlowerpotBookListRv.visibility = View.VISIBLE


        for (item in result) {

            if (item.bookImgUrl != null) {
                Log.d("resp", item.bookImgUrl.toString())

                flowerpotBookList.add(
                    BookImgUrl(bookIdx = item.bookIdx, imgUrl = item.bookImgUrl!!)
                )
            } else {
                Log.d("resp", "test")

                flowerpotBookList.add(
                    BookImgUrl(
                        bookIdx = item.bookIdx,
                        imgUrl = item.bookImgUrl ?: "https://sadad64.shop/images/sunflower_test.png"
                    )
                )
            }
            readingRecordIdxList.add(item.readingRecordIdx)
        }
        adapter.notifyDataSetChanged()
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

        adapter = DetailFlowerpotRVAdapter(flowerpotBookList, readingRecordIdxList)

        binding.detailFlowerpotBookListRv.adapter = adapter
        binding.detailFlowerpotBookListRv.layoutManager = GridLayoutManager(this, 3)
        binding.detailFlowerpotBackBtnIv.setOnClickListener {
            finish()
        }

        //화분에 기록된 책 클릭한 경우 임시로 Toast 메시지
        adapter.setMyItemClickListener(object : DetailFlowerpotRVAdapter.OnItemClickListener {
            override fun onItemClick(bookImgUrl: BookImgUrl, readingRecordIdx: Int) {
                val intent = Intent(applicationContext, DetailHistoryActivity::class.java)
                intent.putExtra("readingRecordIdx", readingRecordIdx)
                startActivity(intent)
            }
        })

        //화분 상세 화면에서 버튼 클릭했을 시 화분 추가 액티비티 나오도록 임시로 설계함. 추후 수정 예정
        binding.detailFlowerpotListBtnIv.setOnClickListener {
            startActivity(Intent(this, AddFlowerpotActivity::class.java))
        }
    }

}