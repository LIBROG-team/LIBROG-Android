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
import com.example.librog.ui.main.addbook.AddBookSelectActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailHistoryActivity :
    BaseActivity<ActivityDetailHistoryBinding>(ActivityDetailHistoryBinding::inflate) {

    private val historyService = ApplicationClass.retrofit.create(HistoryInterface::class.java)

    override fun initAfterBinding() {
        val readingRecordIdx = intent.getIntExtra("readingRecordIdx", -1)

        if (readingRecordIdx == -1) {
            Log.e("Error", "Error")
            finish()
        }

        getDetailReadingRecordByIdx(readingRecordIdx)

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
                        showToast(resp.message)
                        finish()
                    }

                }
            }

            override fun onFailure(call: Call<DetailReadingRecordResponse>, t: Throwable) {
                showToast(t.message?:"DetailHistoryActivity Error")
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
            binding.detailHistoryContentWriteTv.text = result.content ?: ""
            binding.detailHistoryQuoteWriteTv.text = result.quote?: ""
            binding.detailHistoryRatingBarRb.rating = result.starRating.toFloat()
            binding.detailHistoryRatingBarRb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
                ratingBar.rating = result.starRating.toFloat()
            }
            Glide.with(applicationContext)
                .load(result.bookImgUrl)
                .into(detailHistoryBookThumbnailIv)
        }
    }

    // 독서기록 수정 버튼, 독서기록 수정 api 구현 필요
    private fun initClickListener(){
        binding.detailHistoryFixIv.setOnClickListener {
            val intent = Intent(applicationContext, AddBookSelectActivity::class.java)

        }
    }


}