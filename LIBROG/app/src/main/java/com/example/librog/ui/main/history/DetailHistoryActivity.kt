package com.example.librog.ui.main.history


import android.content.Intent
import android.util.Log
import com.bumptech.glide.Glide
import com.example.librog.ApplicationClass
import com.example.librog.R
import com.example.librog.data.remote.history.DetailReadingRecordResponse
import com.example.librog.data.remote.history.DetailReadingRecordResult
import com.example.librog.data.remote.history.HistoryInterface
import com.example.librog.databinding.ActivityDetailHistoryBinding
import com.example.librog.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailHistoryActivity :
    BaseActivity<ActivityDetailHistoryBinding>(ActivityDetailHistoryBinding::inflate) {

    private val historyService = ApplicationClass.retrofit.create(HistoryInterface::class.java)
    private var tempIdx = 0

    override fun initAfterBinding() {
        val readingRecordIdx = intent.getIntExtra("readingRecordIdx", -1)
        tempIdx = readingRecordIdx

        if (readingRecordIdx == -1) {
            Log.e("Error", "Error")
            finish()
        }

        getDetailReadingRecordByIdx(readingRecordIdx)
        initClickListener(readingRecordIdx)

    }

    override fun onResume() {
        super.onResume()
        getDetailReadingRecordByIdxOnResume(tempIdx)
    }

    // API 명세서 2.8 독서기록 상세조회 api
    private fun getDetailReadingRecordByIdx(readingRecordIdx: Int) {
        historyService.getDetailReadingRecordByIdx(readingRecordIdx).enqueue(object :
            Callback<DetailReadingRecordResponse> {
            override fun onResponse(
                call: Call<DetailReadingRecordResponse>,
                response: Response<DetailReadingRecordResponse>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        bind(resp.result)
                    }

                    3015 -> {
                        finish()
                    }
                    else -> {
                        showToast(resp.message)
                    }

                }
            }

            override fun onFailure(call: Call<DetailReadingRecordResponse>, t: Throwable) {
                showToast(t.message ?: "DetailHistoryActivity Error")
                finish()
            }

        })

    }


    fun bind(result: DetailReadingRecordResult) {

        val authors: String = result.author.let {
            it?.joinToString(",")
        } ?: ""


        binding.apply {
            detailHistoryTopTv.text = result.name
            detailHistoryBookTitleTv.text = result.name
            detailHistoryBookAuthorTv.text = authors
            detailHistoryBookContentsTv.text = result.bookInstruction
            detailHistoryStarRatingTv.text =
                String.format(getString(R.string.detail_history_rating), result.starRating, 5)
            detailHistoryContentWriteTv.text = result.content ?: ""
            detailHistoryQuoteWriteTv.text = result.quote ?: ""
            detailHistoryRatingBarRb.rating = result.starRating.toFloat()

// ratingbar 속성에서 srb_isIndicator = true로 변경하면 사용자가 드래그해도 변경되지 않음.
//            detailHistoryRatingBarRb.setOnRatingChangeListener { _, f1, _ ->
//                detailHistoryRatingBarRb.rating = result.starRating.toFloat()
//            }
            Glide.with(applicationContext)
                .load(result.bookImgUrl)
                .into(detailHistoryBookThumbnailIv)
        }

    }

    private fun initClickListener(readingRecordIdx: Int) {
        binding.detailHistoryFixIv.setOnClickListener {
            val intent = Intent(this, HistoryFixActivity::class.java)
            intent.putExtra("readingRecordIdx", readingRecordIdx)
            startActivity(intent)
        }

        binding.detailHistoryBackBtnIv.setOnClickListener {
            finish()
        }
    }


    private fun getDetailReadingRecordByIdxOnResume(readingRecordIdx: Int) {
        historyService.getDetailReadingRecordByIdx(readingRecordIdx).enqueue(object :
            Callback<DetailReadingRecordResponse> {
            override fun onResponse(
                call: Call<DetailReadingRecordResponse>,
                response: Response<DetailReadingRecordResponse>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        bindOnResume(resp.result)
                    }

                    3015 -> {
                        finish()
                    }
                    else -> {
                        showToast(resp.message)
                    }

                }
            }

            override fun onFailure(call: Call<DetailReadingRecordResponse>, t: Throwable) {
                showToast(t.message ?: "DetailHistoryActivity Error")
                finish()
            }

        })

    }

    fun bindOnResume(result: DetailReadingRecordResult) {
        binding.apply {
            detailHistoryStarRatingTv.text =
                String.format(getString(R.string.detail_history_rating), result.starRating, 5)
            detailHistoryContentWriteTv.text = result.content ?: ""
            detailHistoryQuoteWriteTv.text = result.quote ?: ""
            detailHistoryRatingBarRb.rating = result.starRating.toFloat()
        }

    }
}


