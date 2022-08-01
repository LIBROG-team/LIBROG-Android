package com.example.librog.ui.main.addbook

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.librog.R
import com.example.librog.data.entities.Book
import com.example.librog.data.entities.BookImgUrl
import com.example.librog.data.remote.book.Documents
import com.example.librog.databinding.ActivityAddBookSelectBinding
import com.example.librog.ui.main.MainActivity
import com.google.gson.Gson


class AddBookSelectActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBookSelectBinding
    private var gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBookSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBookSelectReviewWriteEt.enableScrollText()
        binding.addBookSelectRateNTv.text =
            String.format(this.getString(R.string.addbook_select_rate_n), 0, 5)
        initClickListener()
        initData()
    }

    private fun initData() {
        val doc = intent.getStringExtra("doc")
        val bookData = gson.fromJson(doc, Documents::class.java)
        val authorList = bookData.authors.joinToString(separator = ",")

        var curBook = Book(
            1,
            bookData.title!!,
            authorList,
            bookData.publisher!!,
            bookData.status!!,
            bookData.datetime!!.slice(
                IntRange(0, 9)
            )
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




    }


    private fun initClickListener() {
        binding.addBookSelectRb.rating = 0.0F
        binding.addBookSelectRb.setOnRatingChangeListener { _, f1, _ ->
            binding.addBookSelectRateNTv.text = "${f1.toInt()} / 5"
        }

        binding.addBookSelectBackBtn.setOnClickListener {
            finish()
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
}