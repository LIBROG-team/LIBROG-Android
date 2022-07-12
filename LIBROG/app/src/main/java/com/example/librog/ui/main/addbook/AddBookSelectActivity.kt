package com.example.librog.ui.main.addbook

import android.content.Intent
import android.os.Bundle
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
}