package com.example.librog.ui.main.addbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.librog.databinding.FragmentAddBookSelectBinding



class AddBookSelectFragment : Fragment() {
    lateinit var binding: FragmentAddBookSelectBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBookSelectBinding.inflate(inflater, container, false)

        binding.addBookSelectRb.setOnRatingChangeListener{
                _, f1, _ ->
            binding.addBookSelectRateNTv.setText("${f1.toInt()} / 5")
        }

        return binding.root

    }


}
