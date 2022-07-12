package com.example.librog.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.librog.R
import com.example.librog.data.DetailTempFlowerpotData
import com.example.librog.data.Readbook
import com.example.librog.databinding.FragmentHomeBinding
import com.example.librog.ui.BaseFragment
import com.example.librog.ui.main.MainActivity
import com.example.librog.ui.main.addbook.AddBookSelectFragment
import com.example.librog.ui.main.flowerpot.DetailFlowerpotRVAdapter

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
            add(Readbook(R.drawable.home_item_book1,"노르웨이의 숲","무라카미 하루키","2022.06.28"))
            add(Readbook(R.drawable.home_item_book2,"공정하다는 착각","마이크 센델","2022.05.06"))
        }


        val readbookRVAdapter = ReadBookRVAdapter(readbookDatas)
        //리사이클러뷰에 어댑터 연결
        binding.homeRecentreadBookRv.adapter = readbookRVAdapter
        binding.homeRecentreadBookRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

//        //프래그먼트 이동(임시)
//        binding.mainCircleFlowerIv.setOnClickListener{
//            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_container,AddBookSelectFragment()).commitAllowingStateLoss()}

        readbookRVAdapter.setMyItemClickListener(object : ReadBookRVAdapter.OnItemClickListener {
            override fun onItemClick(tempReadBookData: Readbook) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_container,AddBookSelectFragment()).commitAllowingStateLoss()
            }
        })

        return binding.root
    }



}