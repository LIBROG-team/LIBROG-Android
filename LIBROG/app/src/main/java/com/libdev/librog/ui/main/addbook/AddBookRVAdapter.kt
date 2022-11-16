package com.libdev.librog.ui.main.addbook

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.libdev.librog.data.remote.book.Documents
import com.libdev.librog.databinding.ItemAddbookBinding

class AddBookRVAdapter(private var bookList: ArrayList<Documents>) :
    RecyclerView.Adapter<AddBookRVAdapter.ViewHolder>() {


    interface OnItemClickListener{
        fun onItemClick(bookData: Documents)
    }

    lateinit var context: Context
    private lateinit var mItemClickListener: OnItemClickListener
    fun setMyItemClickListener(itemClickListener: OnItemClickListener){
        mItemClickListener = itemClickListener
    }

    inner class ViewHolder(val binding: ItemAddbookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Documents) {
            val authorList = book.authors.joinToString(separator = ",")

            binding.itemAddbookTitleTv.text = book.title
            binding.itemAddbookAuthorTv.text = authorList
            binding.itemAddbookContentTv.text = book.contents

            Glide.with(context)
                .load(book.thumbnail)
                .into(binding.itemAddbookBookImgIv)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ItemAddbookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookList[position])
        holder.binding.itemAddbookCl.setOnClickListener {
            mItemClickListener.onItemClick(bookList[position])
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun setDataList(list: ArrayList<Documents>){
        bookList = list
        notifyDataSetChanged()
    }

    fun addDataList(list: ArrayList<Documents>){
        bookList.addAll(list)
        notifyDataSetChanged()
    }
}