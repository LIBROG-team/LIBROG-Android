package com.example.librog.ui.main.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.librog.data.remote.RecentReadResult
import com.example.librog.databinding.ItemHomeRecentBinding

class RecentReadRVAdapter(private val recentReadList: ArrayList<RecentReadResult>) : RecyclerView.Adapter<RecentReadRVAdapter.ViewHolder>() {

    private lateinit var context: Context

    interface OnItemClickListener {
        fun onItemClick(recentReadResult: RecentReadResult)
    }

    private lateinit var mItemClickListener: OnItemClickListener
    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecentReadRVAdapter.ViewHolder {
        context= viewGroup.context
        //아이템뷰 객체 생성
        val binding: ItemHomeRecentBinding = ItemHomeRecentBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: RecentReadRVAdapter.ViewHolder, position: Int) {
        holder.bind(recentReadList[position]) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemHomeBookFrameIv.setOnClickListener {
            mItemClickListener.onItemClick(recentReadList[position])
        }
    }

    override fun getItemCount(): Int = recentReadList.size

    inner class ViewHolder(val binding: ItemHomeRecentBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(recentReadData: RecentReadResult){
            Glide.with(context).load(recentReadData.bookImgUrl).into(binding.itemHomeBookIv)
            binding.itemHomeBookTitleTv.text = recentReadData.bookName

            var authorTotal = ""
            var authorLength=recentReadData.author.size

            for (item in recentReadData.author){
                if (authorLength>1)
                    authorTotal+=String.format("%s, ",item)
                else
                    authorTotal+=item
                authorLength--
            }
            binding.itemHomeBookWriterTv.text=authorTotal

            binding.itemHomeBookDateTv.text = recentReadData.recordedDate.slice(IntRange(0,9))
        }

    }



}