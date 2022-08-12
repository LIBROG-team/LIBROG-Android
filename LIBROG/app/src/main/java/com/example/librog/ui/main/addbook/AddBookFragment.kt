package com.example.librog.ui.main.addbook

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.BuildConfig
import com.example.librog.data.remote.book.BookInterface
import com.example.librog.data.remote.book.BookResponse
import com.example.librog.data.remote.book.Documents
import com.example.librog.databinding.FragmentAddBookBinding
import com.example.librog.ui.BaseFragment
import com.google.gson.Gson
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

        if (bookList.isEmpty()){
            showLogoTv()
        }else{
            showBookList()
        }

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

        binding.addbookCancelCl.setOnClickListener {
            binding.addbookSearchEt.text.clear()
            bookList.clear()
        }

        binding.addbookSearchEt.setOnEditorActionListener { v, actionId, _ ->
            var handled = false

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                bookList.clear()
                val input = binding.addbookSearchEt.text.toString()
                hideKeyboard(v)
                getBooksByName(input)
                handled = true
            }
            handled
        }

        binding.addbookMoreBtn.setOnClickListener {
            pageCount += 1
            getBooksByName(binding.addbookSearchEt.text.toString())
        }


        adapter.setMyItemClickListener(object : AddBookRVAdapter.OnItemClickListener {
            override fun onItemClick(bookData: Documents) {
                val intent = Intent(context, AddBookSelectActivity::class.java)
                val gson = Gson()
                val doc = gson.toJson(bookData)
                intent.putExtra("doc", doc)
                startActivity(intent)
            }
        })

    }


    private fun initBookService() {
        val bookRetrofit = Retrofit.Builder()
            .baseUrl(BOOK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookService = bookRetrofit.create(BookInterface::class.java)
//        bookList.clear()
    }


    private fun getBooksByName(name: String) {
        val searchQuery = "\"$name\""

        bookService.getBooksByName(
            "KakaoAK ${BuildConfig.REST_API_KEY}",
            query = searchQuery,
            pageCount,
            "title"
        ).enqueue(object : Callback<BookResponse> {
            override fun onResponse(
                call: Call<BookResponse>,
                response: Response<BookResponse>
            ) {
                if (response.isSuccessful.not()) {
                    Log.e("result", "NOT SUCCESS")
                    Log.e("result", response.toString())

                } else {
                    val resp = response.body()!!
                    if (resp.documents!!.isEmpty()) {
                        showNobookTv()
                    } else {
                        adapter.addDataList(resp.documents!!)
                        showBookList()
                    }

                    if (resp.meta!!.isEnd == true) {
                        binding.addbookMoreBtn.visibility = View.GONE
                    }


                }

            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("result", t.toString())
            }
        })


    }

    private fun showLogoTv() {
        binding.addbookContentRv.visibility = View.GONE
        binding.addbookNobookTv.visibility = View.GONE
        binding.addbookMoreBtn.visibility = View.GONE
        binding.addbookLogoTv.visibility = View.VISIBLE
    }

    private fun showNobookTv() {
        binding.addbookMoreBtn.visibility = View.GONE
        binding.addbookNobookTv.visibility = View.VISIBLE
        binding.addbookContentRv.visibility = View.GONE
        binding.addbookLogoTv.visibility = View.GONE

    }

    private fun showBookList() {
        binding.addbookMoreBtn.visibility = View.VISIBLE
        binding.addbookLogoTv.visibility = View.GONE
        binding.addbookNobookTv.visibility = View.GONE
        binding.addbookContentRv.visibility = View.VISIBLE
    }

    private fun hideKeyboard(v: View) {
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }


}