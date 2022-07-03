package com.example.librog.ui.main.flowerpot

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.librog.R
import com.example.librog.data.FlowerpotData
import com.example.librog.databinding.ItemFlowerpotBinding

class FlowerpotRVAdapter(private val flowerpotDataList: ArrayList<FlowerpotData>) : RecyclerView.Adapter<FlowerpotRVAdapter.ViewHolder>() {

    lateinit var context: Context

    interface OnItemClickListener {
        fun onItemClick(flowerpotData: FlowerpotData)
    }

    private lateinit var mItemClickListener: OnItemClickListener
    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    inner class ViewHolder(val binding: ItemFlowerpotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(flowerpotData: FlowerpotData) {
            binding.itemFlowerpotTitleTv.text = flowerpotData.name
            binding.itemFlowerpotDateTv.text = String.format(
                context.getString(R.string.flowerpot_date),
                flowerpotData.startDate,
                flowerpotData.endDate
            )
            binding.itemFlowerpotRecordTv.text = String.format(context.getString(R.string.flowerpot_record), flowerpotData.recordBook)
            binding.itemFlowerpotGenreTv.text = String.format(context.getString(R.string.flowerpot_most_genre), flowerpotData.genre)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlowerpotRVAdapter.ViewHolder {
        val binding =
            ItemFlowerpotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlowerpotRVAdapter.ViewHolder, position: Int) {
        holder.bind(flowerpotDataList[position])
        holder.binding.itemFlowerpotCl.setOnClickListener {
            mItemClickListener.onItemClick(flowerpotDataList[position])
        }
    }

    override fun getItemCount(): Int {
        return flowerpotDataList.size

    }
}