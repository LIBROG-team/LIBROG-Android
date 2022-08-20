package com.example.librog.ui.main.flowerpot

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.librog.data.DetailTempFlowerpotData
import com.example.librog.data.entities.BookImgUrl
import com.example.librog.databinding.ItemDetailFlowerpotBinding


class DetailFlowerpotRVAdapter(
    private val items: ArrayList<BookImgUrl>,
    private val readingRecordIdxList: ArrayList<Int>
) : RecyclerView.Adapter<DetailFlowerpotRVAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(bookImgUrl: BookImgUrl, readingRecordIdx: Int)
    }

    private lateinit var mItemClickListener: OnItemClickListener
    private lateinit var context: Context
    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    inner class ViewHolder(val binding: ItemDetailFlowerpotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookImgUrl) {
            Glide.with(context)
                .load(item.imgUrl)
                .into(binding.itemDetailFlowerpotIv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDetailFlowerpotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.itemDetailFlowerpotIv.setOnClickListener {
            mItemClickListener.onItemClick(items[position], readingRecordIdxList[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}