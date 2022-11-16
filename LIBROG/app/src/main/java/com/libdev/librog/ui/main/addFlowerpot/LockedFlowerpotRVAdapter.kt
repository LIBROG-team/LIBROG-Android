package com.libdev.librog.ui.main.addFlowerpot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.libdev.librog.data.remote.data.LockedFpResult
import com.libdev.librog.databinding.ItemLockedFlowerpotBinding

class LockedFlowerpotRVAdapter(
    private var lockedFpList: ArrayList<LockedFpResult>
) : RecyclerView.Adapter<LockedFlowerpotRVAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemLockedFlowerpotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fp: LockedFpResult) {
            binding.lockedFpNameTv.text = fp.name
            binding.lockedFpConditionTv.text = fp.condition
        }
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