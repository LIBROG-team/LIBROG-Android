package com.example.librog.ui.main.flowerpot


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.librog.ApplicationClass
import com.example.librog.R
import com.example.librog.data.entities.BookImgUrl
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.entities.Flowerpot
import com.example.librog.data.remote.data.*
import com.example.librog.data.remote.history.DeleteBookRecordData
import com.example.librog.data.remote.history.FilteredHistoryResult
import com.example.librog.data.remote.history.HistoryInterface
import com.example.librog.data.remote.history.HistoryResponse
import com.example.librog.databinding.ActivityDetailFlowerpotBinding
import com.example.librog.ui.BaseActivity
import com.example.librog.ui.main.history.DetailHistoryActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailFlowerpotActivity :
    BaseActivity<ActivityDetailFlowerpotBinding>(ActivityDetailFlowerpotBinding::inflate) {

    private var flowerpotBookList = ArrayList<BookImgUrl>()
    private var readingRecordIdxList = ArrayList<Int>()
    private lateinit var adapter: DetailFlowerpotRVAdapter
    private var gson = Gson()
    private val historyService = ApplicationClass.retrofit.create(HistoryInterface::class.java)
    private val dataService = ApplicationClass.retrofit.create(DataInterface::class.java)
    private var fpIdx = 1
    private var userFpCount = 1
    private lateinit var curFd: FlowerData
    private lateinit var curFp: Flowerpot
    private var isInitialAccess = true

    override fun initAfterBinding() {
        val curFdJson = intent.getStringExtra("flowerData")
        val curFpJson = intent.getStringExtra("flowerpot")

        fpIdx = intent.getIntExtra("flowerpotIdx", 1)
        userFpCount = intent.getIntExtra("userFpCount", 1)
        curFd = gson.fromJson(curFdJson, FlowerData::class.java)
        curFp = gson.fromJson(curFpJson, Flowerpot::class.java)

        if (curFdJson != null) {
            isInitialAccess = false
        }
        initLayout(curFd, curFp)

    }


    override fun onResume() {
        super.onResume()

        if (!isInitialAccess) {
            refreshLayout(fpIdx)
        }

        getFlowerpotBookRecord(fpIdx)
        initClickListener(fpIdx)

    }

    private fun refreshLayout(fpIdx: Int) {
        dataService.getFpList(getUserIdx()).enqueue(object : Callback<DataResponse1> {
            override fun onResponse(call: Call<DataResponse1>, response: Response<DataResponse1>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        setCurrentFlowerpot(resp.result, fpIdx)
                    }

                    2019 -> {

                    }
                    2020 -> {

                    }
                    else -> {

                    }
                }

            }

            override fun onFailure(call: Call<DataResponse1>, t: Throwable) {
                t.printStackTrace()
            }
        })


    }

    private fun setCurrentFlowerpot(result: ArrayList<FpResult>, fpIdx: Int) {
        for (item in result) {
            if (item.flowerPotIdx == fpIdx) {
                initLayout(
                    FlowerData(
                        item.flowerDataIdx!!,
                        item.name!!,
                        item.engName!!,
                        item.flowerImgUrl ?: "",
                        item.flowerImgUrl ?: "",
                        item.maxExp!!,
                        item.bloomingPeriod!!,
                        item.content ?: "",
                        item.type ?: "",
                        "active"
                    ),
                    Flowerpot(
                        item.flowerPotIdx!!,
                        getUserIdx(),
                        item.flowerDataIdx!!,
                        item.startDate!!.slice(IntRange(0, 9)),
                        item.lastDate!!.slice(IntRange(0, 9)),
                        item.exp!!,
                        item.recordCount!!,
                        "active"
                    )
                )
                break
            }
        }
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


    fun hideFlowerpotRV() {
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

    private fun initLayout(curFd: FlowerData, curFp: Flowerpot) {

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

        adapter.setMyItemClickListener(object : DetailFlowerpotRVAdapter.OnItemClickListener {
            override fun onItemClick(bookImgUrl: BookImgUrl, readingRecordIdx: Int) {
                val intent = Intent(applicationContext, DetailHistoryActivity::class.java)
                intent.putExtra("readingRecordIdx", readingRecordIdx)
                startActivity(intent)
            }
        })


    }


    private fun initClickListener(idx: Int) {
        binding.detailFlowerpotListBtnIv.setOnClickListener {
            showBanner()
        }
        binding.detailFlowerpotBackBtnIv.setOnClickListener {
            finish()
        }

        binding.detailFlowerpotDeleteTv.setOnClickListener {

            AlertDialog.Builder(this)
                .setMessage("화분을 삭제하시겠습니까? 해당 화분의 독서 기록이 영구히 삭제됩니다.")
                .setPositiveButton("확인") { _, _ ->

                    if (userFpCount > 1) {
                        deleteFlowerpot(idx)
                        hideBanner()
                        finish()
                    }
                    else{
                        showToast("화분 개수는 1개 이상이어야 합니다.")
                        hideBanner()
                    }
                }
                .setNegativeButton("취소") { _, _ ->
                    hideBanner()
                }
                .show()


        }

        binding.detailFlowerpotEditRecordTv.setOnClickListener {
            // RecyclerView 다중 선택 필요
            hideBanner()
        }

        binding.detailFlowerpotDarkBackgroundView.setOnClickListener {
            hideBanner()
        }
    }

    private fun deleteFlowerpot(flowerpotIdx: Int) {
        dataService.deleteFlowerpot(flowerpotIdx).enqueue(object : Callback<DeleteFpResponse> {
            override fun onResponse(
                call: Call<DeleteFpResponse>,
                response: Response<DeleteFpResponse>
            ) {
                val resp = response.body()!!
                when (resp.result.code) {
                    1000 -> {
                        showToast("화분 삭제에 성공하였습니다.")
                        finish()
                    }
                    else -> {
                        showToast("화분 삭제에 실패하였습니다: ${resp.result.message}")

                    }
                }
            }

            override fun onFailure(call: Call<DeleteFpResponse>, t: Throwable) {
                showToast("화분 삭제에 실패하였습니다.")
            }

        })
    }


    private fun hideBanner() {
        binding.detailFlowerpotBannerCl.visibility = View.INVISIBLE
        binding.detailFlowerpotDarkBackgroundView.visibility = View.INVISIBLE
        binding.detailFlowerpotListBtnIv.isClickable = true
    }

    private fun showBanner() {
        binding.detailFlowerpotBannerCl.visibility = View.VISIBLE
        binding.detailFlowerpotDarkBackgroundView.visibility = View.VISIBLE
        binding.detailFlowerpotListBtnIv.isClickable = false
    }

    private fun getUserIdx(): Int {
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("idx", -1)
    }

}