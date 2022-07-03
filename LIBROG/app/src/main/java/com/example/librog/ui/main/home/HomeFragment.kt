package com.example.librog.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.librog.R
import com.example.librog.data.Readbook
import com.example.librog.databinding.FragmentHomeBinding
import com.example.librog.ui.BaseFragment

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private var readbookDatas = ArrayList<Readbook>() //album data class

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        readbookDatas.apply{
            add(Readbook("사피엔스","유발 하라리","2022.05.06"))
            add(Readbook("공정하다는 착각","마이크 센델","2022.05.17"))
            add(Readbook("사피엔스","유발 하라리","2021.05.06"))
        }


        val readbookRVAdapter = ReadBookRVAdapter(readbookDatas)
        //리사이클러뷰에 어댑터 연결
        binding.homeRecentreadBookRv.adapter = readbookRVAdapter
        binding.homeRecentreadBookRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        return binding.root
    }



}