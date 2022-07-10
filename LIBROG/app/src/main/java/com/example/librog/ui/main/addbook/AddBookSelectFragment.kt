package com.example.librog.ui.main.addbook

import android.content.Context
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




        return binding.root
    }



}
