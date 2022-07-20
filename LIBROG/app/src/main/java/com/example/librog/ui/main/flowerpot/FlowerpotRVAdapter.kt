package com.example.librog.ui.main.flowerpot

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.librog.R
import com.example.librog.data.entities.FlowerData
import com.example.librog.databinding.ItemFlowerpotBinding

class FlowerpotRVAdapter(private val flowerDataList: ArrayList<FlowerData>) : RecyclerView.Adapter<FlowerpotRVAdapter.ViewHolder>() {

    lateinit var context: Context

    interface OnItemClickListener {
        fun onItemClick(flowerData: FlowerData)
    }

    private lateinit var mItemClickListener: OnItemClickListener
    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    inner class ViewHolder(val binding: ItemFlowerpotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(flowerData: FlowerData) {
            binding.itemFlowerpotTitleTv.text = flowerData.name
//            binding.itemFlowerpotDateTv.text = String.format(
//                context.getString(R.string.flowerpot_date),
//                flowerData.startDate,
//                flowerData.lastDate
//            )
//            binding.itemFlowerpotRecordTv.text = String.format(context.getString(R.string.flowerpot_record), flowerData.recordCount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerpotRVAdapter.ViewHolder {
        val binding =
            ItemFlowerpotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlowerpotRVAdapter.ViewHolder, position: Int) {
        holder.bind(flowerDataList[position])
        holder.binding.itemFlowerpotCl.setOnClickListener {
            mItemClickListener.onItemClick(flowerDataList[position])
        }
        Glide.with(context)
            .load(flowerDataList[position].flowerpotImgUrl)
            .into(holder.binding.itemFlowerpotIv)
    }

    override fun getItemCount(): Int {
        return flowerDataList.size

    }
}