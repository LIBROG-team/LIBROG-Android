package com.example.librog.ui.main.addbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.librog.data.entities.Book
import com.example.librog.databinding.ItemAddbookBinding

class AddBookRVAdapter(private var bookList: ArrayList<Book>) :
    RecyclerView.Adapter<AddBookRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemAddbookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.itemAddbookTitleTv.text = book.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAddbookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun setDataList(list: ArrayList<Book>){
        bookList = list
        notifyDataSetChanged()
    }
}