package com.example.librog.ui.main.addFlowerpot

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.librog.data.entities.FlowerData
import com.example.librog.databinding.ItemLockedFlowerpotBinding

class LockedFlowerpotRVAdapter():RecyclerView.Adapter<LockedFlowerpotRVAdapter.ViewHolder>(){
    private val lockedFpList = ArrayList<FlowerData>()


    inner class ViewHolder(val binding: ItemLockedFlowerpotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(flowerpot: FlowerData) {
            binding.lockedFpNameTv.text = flowerpot.name
            binding.lockedFpConditionTv.text = flowerpot.blooming
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addLockedFlowerpot(unlockedFlowerpots: ArrayList<FlowerData>) {
        this.lockedFpList.clear()
        this.lockedFpList.addAll(unlockedFlowerpots)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLockedFlowerpotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lockedFpList[position])
    }

    override fun getItemCount(): Int {
        return lockedFpList.size
    }
}