package com.libdev.librog.ui.main.history

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.bumptech.glide.Glide
import com.libdev.librog.ApplicationClass
import com.libdev.librog.R
import com.libdev.librog.data.remote.history.*
import com.libdev.librog.databinding.ActivityHistoryFixBinding
import com.libdev.librog.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryFixActivity :
    BaseActivity<ActivityHistoryFixBinding>(ActivityHistoryFixBinding::inflate) {

    private val historyService = ApplicationClass.retrofit.create(HistoryInterface::class.java)

    override fun initAfterBinding() {
        val readingRecordIdx = intent.getIntExtra("readingRecordIdx", -1)

        if (readingRecordIdx == -1) {
            Log.e("Error", "Error")
            finish()
        }

        initData(readingRecordIdx)
        initClickListener(readingRecordIdx)
    }

    private fun afterUserInput(readingRecordIdx: Int): FixBookRecordData {

        return FixBookRecordData(
            binding.historyFixRb.rating.toInt(),
            binding.historyFixLineWriteEt.text.toString(),
            binding.historyFixReviewWriteEt.text.toString(),
            readingRecordIdx
        )
    }


    private fun initDeleteDialog(readingRecordIdx: Int) {
        AlertDialog.Builder(this)
            .setMessage("${binding.historyFixTitleTv.text}을(를) 삭제하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                deleteReadingRecord(DeleteBookRecordData(readingRecordIdx))
            }
            .setNegativeButton("취소", null)
            .show()

    }

    private fun initClickListener(readingRecordIdx: Int) {
        binding.historyFixDeleteIv.setOnClickListener {
            initDeleteDialog(readingRecordIdx)
            hideKeyboard(it)
        }

        binding.historyFixBackBtnIv.setOnClickListener {
            finish()
        }

        binding.historyFixFinishBtn.setOnClickListener {
            fixReadingRecord(afterUserInput(readingRecordIdx))
            hideKeyboard(it)
            finish()

        }

    }


    // 2.4 독서기록 수정 PATCH api
    private fun fixReadingRecord(fixBookRecord: FixBookRecordData) {
        historyService.fixReadingRecord(fixBookRecord).enqueue(object : Callback<FixResponse> {
            override fun onResponse(call: Call<FixResponse>, response: Response<FixResponse>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        showToast("독서기록 수정에 성공하였습니다.")

                    }

                    else -> {
                        showToast(resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<FixResponse>, t: Throwable) {
                t.printStackTrace()
                Log.d("error", t.message.toString())
            }


        })
    }


    //2.5 독서기록 삭제 DELETE api
    private fun deleteReadingRecord(readingRecordIdx: DeleteBookRecordData) {
        historyService.deleteReadingRecord(readingRecordIdx)
            .enqueue(object : Callback<DeleteRecordResponse> {
                override fun onResponse(
                    call: Call<DeleteRecordResponse>,
                    response: Response<DeleteRecordResponse>
                ) {
                    val resp = response.body()!!
                    when (resp.code) {
                        1000 -> {
                            showToast("${binding.historyFixTitleTv.text} 삭제에 성공하였습니다.")
                            finish()
                        }
                        else -> {
                            showToast(resp.message)
                        }
                    }
                }

                override fun onFailure(call: Call<DeleteRecordResponse>, t: Throwable) {
                    t.printStackTrace()
                    finish()
                }

            })
    }

    private fun initData(readingRecordIdx: Int) {
        historyService.getDetailReadingRecordByIdx(readingRecordIdx).enqueue(object :
            Callback<DetailReadingRecordResponse> {
            override fun onResponse(
                call: Call<DetailReadingRecordResponse>,
                response: Response<DetailReadingRecordResponse>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        setCurrentData(resp.result)
                    }

                    else -> {
                        showToast(resp.message)
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<DetailReadingRecordResponse>, t: Throwable) {
                showToast(t.message ?: "DetailHistoryActivity Error")
                finish()
            }

        })

    }

    @SuppressLint("ClickableViewAccessibility")
    fun EditText.enableScrollText() {
        overScrollMode = View.OVER_SCROLL_ALWAYS
        scrollBarStyle = View.SCROLLBARS_INSIDE_INSET
        isVerticalScrollBarEnabled = true
        setOnTouchListener { view, event ->
            if (view is EditText) {
                if (!view.text.isNullOrEmpty()) {
                    view.parent.requestDisallowInterceptTouchEvent(true)
                    when (event.action and MotionEvent.ACTION_MASK) {
                        MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(
                            false
                        )
                    }
                }
            }
            false
        }
    }

    fun setCurrentData(result: DetailReadingRecordResult) {
        val authors: String = result.author.let {
            it?.joinToString(",")
        } ?: ""

        binding.apply {
            historyFixTopTextTv.text = result.name
            historyFixTitleTv.text = result.name
            historyFixAuthorTv.text = authors
            historyFixExplainTv.text = result.bookInstruction
            historyFixRateNTv.text =
                String.format(getString(R.string.detail_history_rating), result.starRating, 5)
            binding.historyFixReviewWriteEt.setText(result.content)
            historyFixReviewWriteEt.enableScrollText()
            binding.historyFixLineWriteEt.setText(result.quote)
            binding.historyFixRb.rating = result.starRating.toFloat()
            binding.historyFixRb.setOnRatingChangeListener { ratingBar, rating, fromUser ->
                binding.historyFixRateNTv.text = "${rating.toInt()} / 5"
            }
            Glide.with(applicationContext)
                .load(result.bookImgUrl)
                .into(historyFixBookThumbnailIv)
        }

    }
}