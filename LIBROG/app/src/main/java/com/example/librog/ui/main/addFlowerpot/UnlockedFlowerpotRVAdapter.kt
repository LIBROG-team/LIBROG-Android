package com.example.librog.ui.main.addFlowerpot

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.librog.data.entities.FlowerData
import com.example.librog.databinding.ItemUnlockedFlowerpotBinding

class UnlockedFlowerpotRVAdapter() : RecyclerView.Adapter<UnlockedFlowerpotRVAdapter.ViewHolder>() {

    private val unlockedFpList = ArrayList<FlowerData>()

    private lateinit var mItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(flowerpot: FlowerData)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mItemClickListener = onItemClickListener
    }

    inner class ViewHolder(val binding: ItemUnlockedFlowerpotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(flowerpot: FlowerData) {
            binding.unlockedFpNameTv.text = flowerpot.name
            binding.unlockedFpStatusTv.text = flowerpot.status
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addUnlockedFlowerpot(unlockedFlowerpots: ArrayList<FlowerData>) {
        this.unlockedFpList.clear()
        this.unlockedFpList.addAll(unlockedFlowerpots)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUnlockedFlowerpotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(unlockedFpList[position])
        holder.binding.itemUnlockedFlowerpotCl.setOnClickListener {
            mItemClickListener.onItemClick(unlockedFpList[position])
        }
    }

    override fun getItemCount(): Int {
        return unlockedFpList.size
    }

}