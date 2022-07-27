package com.example.librog.ui.main.addFlowerpot

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.librog.data.entities.FlowerData
import com.example.librog.data.remote.data.UnlockedFpResult
import com.example.librog.databinding.ItemUnlockedFlowerpotBinding

class UnlockedFlowerpotRVAdapter(
    private var unlockedFpList: ArrayList<UnlockedFpResult>
) : RecyclerView.Adapter<UnlockedFlowerpotRVAdapter.ViewHolder>() {

    lateinit var context: Context
    private lateinit var mItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(fp: UnlockedFpResult)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mItemClickListener = onItemClickListener
    }

    inner class ViewHolder(val binding: ItemUnlockedFlowerpotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fp: UnlockedFpResult) {
            Log.d("resp", fp.toString())
            binding.unlockedFpNameTv.text = fp.name
            binding.unlockedFpStatusTv.text = fp.type
            binding.unlockedFpBloomingTv.text = fp.bloomingPeriod
            Glide.with(context)
                .load(fp.flowerImgUrl)
                .into(binding.unlockedFpImgIv)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUnlockedFlowerpotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context

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