package com.example.librog.ui.main.addbook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.librog.R
import com.example.librog.databinding.ActivityAddBookSelectBinding
import com.example.librog.ui.main.MainActivity


class AddBookSelectActivity : AppCompatActivity(){

    lateinit var binding: ActivityAddBookSelectBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBookSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBookSelectReviewWriteEt.enableScrollText();
        initClickListener()

    }

    private fun initClickListener(){
        binding.addBookSelectRb.setOnRatingChangeListener{
                _, f1, _ ->
            binding.addBookSelectRateNTv.setText("${f1.toInt()} / 5")
        }

        binding.addBookSelectBackBtn.setOnClickListener {
            finish()

        }
    }

    fun EditText.enableScrollText()
    {
        overScrollMode = View.OVER_SCROLL_ALWAYS
        scrollBarStyle = View.SCROLLBARS_INSIDE_INSET
        isVerticalScrollBarEnabled = true
        setOnTouchListener { view, event ->
            if (view is EditText) {
                if(!view.text.isNullOrEmpty()) {
                    view.parent.requestDisallowInterceptTouchEvent(true)
                    when (event.action and MotionEvent.ACTION_MASK) {
                        MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
            false
        }
    }
}