package com.libdev.librog.ui.main.addbook

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.libdev.librog.ApplicationClass
import com.libdev.librog.R
import com.libdev.librog.data.entities.Book
import com.libdev.librog.data.remote.book.Documents
import com.libdev.librog.data.remote.history.AddBookResponse
import com.libdev.librog.data.remote.history.HistoryInterface
import com.libdev.librog.data.remote.history.UserBookRecord
import com.libdev.librog.databinding.ActivityAddBookSelectBinding
import com.libdev.librog.ui.BaseActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddBookSelectActivity :
    BaseActivity<ActivityAddBookSelectBinding>(ActivityAddBookSelectBinding::inflate) {

    private var gson = Gson()
    private val historyService = ApplicationClass.retrofit.create(HistoryInterface::class.java)
    private lateinit var curBook: Book
    private lateinit var curUserBookRecord: UserBookRecord

    override fun initAfterBinding() {
        val userIdx = getIdx()

        binding.addBookSelectReviewWriteEt.enableScrollText()
        binding.addBookSelectRateNTv.text =
            String.format(this.getString(R.string.addbook_select_rate_n), 1, 5)
        initData(userIdx)
        initClickListener()

    }


    private fun initData(userIdx: Int) {
        val doc = intent.getStringExtra("doc")
        val bookData = gson.fromJson(doc, Documents::class.java)
        val authorList = bookData.authors.joinToString(separator = ",")
        val publishedDate = bookData.datetime!!.slice(IntRange(0, 9))


        curBook = Book(
            name = bookData.title!!,
            author = authorList,
            publisher = bookData.publisher!!,
            status = bookData.status!!,
            publishedDate = publishedDate
        )

        curUserBookRecord = UserBookRecord(
            curBook.name,
            bookData.authors,
            bookData.publisher,
            publishedDate,
            bookData.contents,
            bookData.thumbnail,
            userIdx,
            0,
            "",
            ""
        )

        if (bookData.thumbnail!! == "") {
            binding.addBookSelectBookIv.setImageResource(R.drawable.test_flower_img)
        } else {
            Glide.with(applicationContext)
                .load(bookData.thumbnail)
                .into(binding.addBookSelectBookIv)
        }

        binding.addBookSelectTitleTv.text = curBook.name
        binding.addBookSelectWriterTv.text = curBook.author
        binding.addBookSelectExplainTv.text = bookData.contents
        binding.addBookSelectBackTv.text = curBook.name

    }


    private fun initClickListener() {
        binding.addBookSelectRb.rating = 0.0F
        binding.addBookSelectRb.setOnRatingChangeListener { _, f1, _ ->
            binding.addBookSelectRateNTv.text = "${f1.toInt()} / 5"
        }

        binding.addBookSelectBackBtn.setOnClickListener {
            finish()
        }

        binding.addBookSelectFinishBtn.setOnClickListener {
            postUserInput()
            addUserBookRecord(curUserBookRecord)
            finish()
        }

    }


    // 2.5 독서기록 추가 api
    private fun addUserBookRecord(userBookRecord: UserBookRecord) {
        historyService.addUserBookRecord(userBookRecord).enqueue(object :
            Callback<AddBookResponse> {
            override fun onResponse(
                call: Call<AddBookResponse>,
                response: Response<AddBookResponse>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        Log.d("AddBookSelect", resp.toString())
                        showToast(resp.message)
                    }
                    2025 -> {
                        showToast(resp.message)
                    }
                    3005 -> {
                        showToast(resp.message)
                    }
                    3007 -> {
                        showToast(resp.message)
                    }
                    else -> {
                        showToast(resp.message)
                    }
                }

            }

            override fun onFailure(call: Call<AddBookResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }


    private fun postUserInput() {
        curUserBookRecord.starRating = binding.addBookSelectRb.rating.toInt()

        val quote = binding.addBookSelectLineWriteEt.text.toString()
        val content = binding.addBookSelectReviewWriteEt.text.toString()

        if (quote.isNotEmpty() or content.isNotEmpty()) {
            curUserBookRecord.quote = quote
            curUserBookRecord.content = content
        }


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

    private fun getIdx(): Int {
        val spf = getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx", -1)
    }


}