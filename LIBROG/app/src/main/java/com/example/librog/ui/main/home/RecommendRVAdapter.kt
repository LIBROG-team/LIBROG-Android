package com.example.librog.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.librog.data.RecommendData
import com.example.librog.databinding.ItemHomeBookBinding
import com.example.librog.databinding.ItemHomeRecommendBinding

class RecommendRVAdapter(
    private var recommendList: ArrayList<RecommendData>
) : RecyclerView.Adapter<RecommendRVAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(tempReadBookData: RecommendData)
    }

    private lateinit var mItemClickListener: OnItemClickListener
    fun setMyItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecommendRVAdapter.ViewHolder {
        //아이템뷰 객체 생성
        val binding: ItemHomeRecommendBinding = ItemHomeRecommendBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }

    override fun onBindViewHolder(holder: RecommendRVAdapter.ViewHolder, position: Int) {
        holder.bind(recommendList[position]) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
//        holder.binding.itemHomeBookFrameIv.setOnClickListener {
//            mItemClickListener.onItemClick(recommendList[position])
//        }
    }

    override fun getItemCount(): Int = recommendList.size

    inner class ViewHolder(val binding: ItemHomeRecommendBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(recommendData: RecommendData){
            binding.itemHomeRcBook.setImageResource(recommendData.imgUrl!!)
            binding.itemHomeRcTitle.text = recommendData.title
            binding.itemHomeRcAuthor.text = recommendData.author
        }

    }
}