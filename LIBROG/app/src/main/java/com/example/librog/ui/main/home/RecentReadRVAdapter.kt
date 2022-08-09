package com.example.librog.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.librog.data.RecentReadData
import com.example.librog.databinding.ItemHomeBookBinding

class ReadBookRVAdapter(private val readBookList: ArrayList<RecentReadData>) : RecyclerView.Adapter<ReadBookRVAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(tempReadBookData: RecentReadData)
    }

    private lateinit var mItemClickListener: OnItemClickListener
    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ReadBookRVAdapter.ViewHolder {
        //아이템뷰 객체 생성
        val binding: ItemHomeBookBinding = ItemHomeBookBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: ReadBookRVAdapter.ViewHolder, position: Int) {
        holder.bind(readBookList[position]) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemHomeBookFrameIv.setOnClickListener {
            mItemClickListener.onItemClick(readBookList[position])
        }
    }

    override fun getItemCount(): Int = readBookList.size

    inner class ViewHolder(val binding: ItemHomeBookBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(readBook: RecentReadData){
            binding.itemHomeBookIv.setImageResource(readBook.coverImg!!)
            binding.itemHomeBookTitleTv.text = readBook.title
            binding.itemHomeBookWriterTv.text = readBook.writer
            binding.itemHomeBookDateTv.text = readBook.date
        }

    }



}