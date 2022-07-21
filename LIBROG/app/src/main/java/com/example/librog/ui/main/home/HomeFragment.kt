package com.example.librog.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.R
import com.example.librog.data.ReadBook
import com.example.librog.databinding.FragmentHomeBinding
import com.example.librog.ui.main.addbook.AddBookSelectActivity


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private var readBookData = ArrayList<ReadBook>() //album data class

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        readBookData.apply{
            add(ReadBook(R.drawable.home_item_book1,"노르웨이의 숲","무라카미 하루키","2022.06.28"))
            add(ReadBook(R.drawable.home_item_book2,"공정하다는 착각","마이크 센델","2022.05.06"))
        }


        val readbookRVAdapter = ReadBookRVAdapter(readBookData)
        //리사이클러뷰에 어댑터 연결
        binding.homeRecentreadBookRv.adapter = readbookRVAdapter
        binding.homeRecentreadBookRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

//        //프래그먼트 이동(임시)
//        binding.mainCircleFlowerIv.setOnClickListener{
//            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_container,AddBookSelectFragment()).commitAllowingStateLoss()}


        readbookRVAdapter.setMyItemClickListener(object : ReadBookRVAdapter.OnItemClickListener {
            override fun onItemClick(tempReadBookData: ReadBook) {
                startActivity(Intent(context, AddBookSelectActivity::class.java))
            }
        })


        return binding.root
    }



}