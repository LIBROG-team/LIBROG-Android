package com.example.librog.ui.main.history

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.librog.data.HistoryBookData
import com.example.librog.databinding.ItemHistoryBookBinding


class HistoryRVAdapter (private val historyBookList: ArrayList<HistoryBookData>) : RecyclerView.Adapter<HistoryRVAdapter.ViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(tempHistoryBookData: HistoryBookData)
    }



    private lateinit var mItemClickListener: OnItemClickListener
    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HistoryRVAdapter.ViewHolder {
        //아이템뷰 객체 생성
        val binding: ItemHistoryBookBinding = ItemHistoryBookBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: HistoryRVAdapter.ViewHolder, position: Int) {
        holder.bind(historyBookList[position]) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemHistoryBookIv.setOnClickListener {
            mItemClickListener.onItemClick(historyBookList[position])
        }
    }

    override fun getItemCount(): Int = historyBookList.size

    inner class ViewHolder(val binding: ItemHistoryBookBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(historybook: HistoryBookData){
            binding.itemHistoryBookIv.setImageResource(historybook.coverImg!!)
        }

    }


}





