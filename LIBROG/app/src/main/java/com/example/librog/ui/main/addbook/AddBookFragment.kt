package com.example.librog.ui.main.addbook

import android.content.Context
import android.os.Build.VERSION_CODES.P
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.BuildConfig
import com.example.librog.data.entities.Book
import com.example.librog.databinding.FragmentAddBookBinding
import com.example.librog.ui.BaseFragment
import java.util.*
import kotlin.collections.ArrayList

class AddBookFragment : BaseFragment<FragmentAddBookBinding>(FragmentAddBookBinding::inflate) {
    private lateinit var imm: InputMethodManager
    private var bookList = ArrayList<Book>()
    private var searchList = ArrayList<Book>()
    private lateinit var adapter: AddBookRVAdapter
    private val textWatcher = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            Log.d("EDITTEXT", binding.addbookSearchEt.text.toString())
            val editText = binding.addbookSearchEt.text.toString()
            searchList.clear()

            if(editText == ""){
                adapter.setDataList(bookList)
            }
            else{
                for (i in 0..bookList.size){

                }
            }
        }
    }

    override fun initAfterBinding() {
        initData()


        adapter = AddBookRVAdapter(bookList)
        binding.addbookContentRv.adapter = adapter
        binding.addbookContentRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.addbookContentRv.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.addbookSearchCl.setOnClickListener {
            binding.addbookSearchEt.requestFocus()
            imm.showSoftInput(binding.addbookSearchEt, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.addbookCancelIv.setOnClickListener {
            binding.addbookSearchEt.text.clear()
        }

        binding.addbookSearchEt.addTextChangedListener(textWatcher)





    }

    private fun initData() {
        Log.d("TEST", "${BuildConfig.REST_API_KEY}")
        bookList.addAll(
            arrayListOf(
                Book(1, "one", "temp", "temp", "temp", "temp"),
                Book(2, "two", "temp", "temp", "temp", "temp"),
                Book(3, "three", "temp", "temp", "temp", "temp"),
                Book(3, "four", "temp", "temp", "temp", "temp"),
                Book(3, "five", "temp", "temp", "temp", "temp"),
                Book(3, "six", "temp", "temp", "temp", "temp"),
                Book(3, "seven", "temp", "temp", "temp", "temp"),
                Book(3, "eight", "temp", "temp", "temp", "temp"),
                Book(3, "nine", "temp", "temp", "temp", "temp"),
                Book(3, "ten", "temp", "temp", "temp", "temp"),
            )
        )

    }


}