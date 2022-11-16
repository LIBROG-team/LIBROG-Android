package com.libdev.librog.ui.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.libdev.librog.data.remote.RecommendResult
import com.libdev.librog.databinding.ItemHomeRecommendBinding

class RecommendRVAdapter(private val recommendList: ArrayList<RecommendResult>) : RecyclerView.Adapter<RecommendRVAdapter.ViewHolder>() {

    private lateinit var context: Context
    interface OnItemClickListener {
        fun onItemClick(recommendData: RecommendResult)
    }

    private lateinit var mItemClickListener: OnItemClickListener
    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecommendRVAdapter.ViewHolder {
        context= viewGroup.context
        //아이템뷰 객체 생성
        val binding: ItemHomeRecommendBinding = ItemHomeRecommendBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: RecommendRVAdapter.ViewHolder, position: Int) {
        holder.bind(recommendList[position]) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.binding.itemHomeRcBook.setOnClickListener {
            mItemClickListener.onItemClick(recommendList[position])
        }
    }

    override fun getItemCount(): Int = recommendList.size

    inner class ViewHolder(val binding: ItemHomeRecommendBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(recommendData: RecommendResult){
            Glide.with(context).load(recommendData.bookCoverImg).into(binding.itemHomeRcBook)
            binding.itemHomeRcTitle.text = recommendData.name
            binding.itemHomeRcAuthor.text = recommendData.author
            binding.itemHomeRcPublisher.text = recommendData.publisher
        }

    }
}