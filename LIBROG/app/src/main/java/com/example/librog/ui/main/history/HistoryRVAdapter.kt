package com.example.librog.ui.main.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.librog.data.HistoryBookData
import com.example.librog.data.entities.BookImgUrl
import com.example.librog.databinding.ItemHistoryBookBinding


class HistoryRVAdapter(
    private val historyBookList: ArrayList<BookImgUrl>,
    private var readingRecordIdxList: ArrayList<Int>
) : RecyclerView.Adapter<HistoryRVAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var mItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(bookImgUrl: BookImgUrl, readingRecordIdx: Int)
    }


    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): HistoryRVAdapter.ViewHolder {
        //아이템뷰 객체 생성
        context = viewGroup.context
        val binding: ItemHistoryBookBinding =
            ItemHistoryBookBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: HistoryRVAdapter.ViewHolder, position: Int) {
        holder.bind(historyBookList[position]) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemHistoryBookIv.setOnClickListener {
            mItemClickListener.onItemClick(historyBookList[position], readingRecordIdxList[position])
        }
    }

    override fun getItemCount(): Int = historyBookList.size

    inner class ViewHolder(val binding: ItemHistoryBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bookImgUrl: BookImgUrl) {
            Glide.with(context)
                .load(bookImgUrl.imgUrl)
                .into(binding.itemHistoryBookIv)
        }

    }


}





