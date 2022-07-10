package com.example.librog.ui.main.flowerpot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.librog.data.DetailTempFlowerpotData
import com.example.librog.databinding.ItemDetailFlowerpotBinding


class DetailFlowerpotRVAdapter(private val items: ArrayList<DetailTempFlowerpotData>): RecyclerView.Adapter<DetailFlowerpotRVAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(tempFlowerpotData: DetailTempFlowerpotData)
    }

    private lateinit var mItemClickListener: OnItemClickListener
    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    inner class ViewHolder(val binding: ItemDetailFlowerpotBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: DetailTempFlowerpotData){
            binding.itemDetailFlowerpotIv.setImageResource(item.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDetailFlowerpotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.itemDetailFlowerpotIv.setOnClickListener {
            mItemClickListener.onItemClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}