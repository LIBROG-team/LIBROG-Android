package com.example.librog.ui.main.addbook

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.BuildConfig
import com.example.librog.data.entities.Book
import com.example.librog.data.remote.book.BookInterface
import com.example.librog.data.remote.book.BookResponse
import com.example.librog.data.remote.book.Documents
import com.example.librog.databinding.FragmentAddBookBinding
import com.example.librog.ui.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddBookFragment : BaseFragment<FragmentAddBookBinding>(FragmentAddBookBinding::inflate) {

    companion object {
        const val BOOK_BASE_URL: String = "https://dapi.kakao.com"
    }

    private var bookList = ArrayList<Documents>()
    private lateinit var adapter: AddBookRVAdapter
    private lateinit var imm: InputMethodManager
    private lateinit var bookService: BookInterface
    private var pageCount = 1

    override fun initAfterBinding() {
        initBookService()


        adapter = AddBookRVAdapter(bookList)
        binding.addbookContentRv.adapter = adapter
        binding.addbookContentRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.addbookContentRv.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )

        imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.addbookSearchCl.setOnClickListener {
            binding.addbookSearchEt.requestFocus()
            imm.showSoftInput(binding.addbookSearchEt, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.addbookCancelIv.setOnClickListener {
            binding.addbookSearchEt.text.clear()
        }

        binding.addbookSearchEt.setOnEditorActionListener { v, actionId, event ->
            var handled = false

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val input = binding.addbookSearchEt.text.toString()
                hideKeyboard(v)
                getBooksByName(input)
                afterDataInserted()
                handled = true
            }
            handled
        }

    }


    private fun initBookService() {
        val bookRetrofit = Retrofit.Builder()
            .baseUrl(BOOK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookService = bookRetrofit.create(BookInterface::class.java)
    }


    private fun getBooksByName(name: String) {
        val searchQuery = "\"$name\""
        Log.d("search", searchQuery)

        bookService.getBooksByName("KakaoAK ${BuildConfig.REST_API_KEY}", query = searchQuery, pageCount, "title")
            .enqueue(object : Callback<BookResponse> {
                override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                    if(response.isSuccessful.not()){
                        Log.e("result", "NOT SUCCESS")
                        Log.e("result", response.toString())

                    }else{
                        val resp = response.body()!!
                        if (resp.documents != null) {
                            adapter.setDataList(resp.documents!!)


                        }

                    }




                }

                override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                    Log.e("result", t.toString())
                }
            })


    }


    fun afterDataInserted() {
        binding.addbookNobookTv.visibility = View.GONE
        binding.addbookContentRv.visibility = View.VISIBLE
    }

    fun hideKeyboard(v: View) {
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }


}