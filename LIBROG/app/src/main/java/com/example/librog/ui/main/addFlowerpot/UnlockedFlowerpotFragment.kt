package com.example.librog.ui.main.addFlowerpot

import android.content.Context
import android.content.Intent

import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.ApplicationClass
import com.example.librog.data.remote.data.DataInterface
import com.example.librog.data.remote.data.DataResponse2
import com.example.librog.data.remote.data.UnlockedFpResult
import com.example.librog.databinding.FragmentUnlockedFlowerpotBinding
import com.example.librog.ui.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnlockedFlowerpotFragment :
    BaseFragment<FragmentUnlockedFlowerpotBinding>(FragmentUnlockedFlowerpotBinding::inflate) {
    private var unlockedFpList = ArrayList<UnlockedFpResult>()
    private val dataService = ApplicationClass.retrofit.create(DataInterface::class.java)
    private lateinit var imm: InputMethodManager
    private lateinit var adapter: UnlockedFlowerpotRVAdapter


    override fun initAfterBinding() {
        val userIdx = getUserIdx()
        unlockedFpList.clear()
        imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        getUnlockedFpResult(userIdx)
        initUnlockedRV()
        initClickListener(userIdx)
    }

    private fun initClickListener(userIdx: Int) {
        binding.unlockedFlowerpotSearchCl.setOnClickListener {
            binding.unlockedFlowerpotSearchEt.requestFocus()
            imm.showSoftInput(binding.unlockedFlowerpotSearchEt, InputMethodManager.SHOW_IMPLICIT)
        }


        binding.unlockedFlowerpotCancelCl.setOnClickListener {
            binding.unlockedFlowerpotSearchEt.text.clear()
            unlockedFpList.clear()
            getUnlockedFpResult(userIdx)
        }


        binding.unlockedFlowerpotSearchEt.setOnEditorActionListener { v, actionId, _ ->
            var handled = false

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                unlockedFpList.clear()
                val input = binding.unlockedFlowerpotSearchEt.text.toString()
                hideKeyboard(v)
                searchUnlockedFpResult(userIdx, input)
                handled = true
            }
            handled
        }

    }


    private fun hideKeyboard(v: View) {
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    private fun setUnlockedFpList(result: ArrayList<UnlockedFpResult>) {
        unlockedFpList.addAll(result)
        adapter.notifyDataSetChanged()
    }


    //획득 화분 정보 가져오기 api
    private fun getUnlockedFpResult(userIdx: Int) {

        dataService.getUnlockedFpResult(userIdx).enqueue(object : Callback<DataResponse2> {
            override fun onResponse(call: Call<DataResponse2>, response: Response<DataResponse2>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        setUnlockedFpList(resp.result)
                    }

                    2019 -> {

                    }
                    2020 -> {

                    }
                    else -> {

                    }
                }
            }

            override fun onFailure(call: Call<DataResponse2>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }

    // 획득 화분 검색 api
    private fun searchUnlockedFpResult(userIdx: Int, name: String) {
        dataService.searchUnlockedFpResult(userIdx, name).enqueue(object : Callback<DataResponse2> {
            override fun onResponse(call: Call<DataResponse2>, response: Response<DataResponse2>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        setUnlockedFpList(resp.result)
                    }
                    else -> {
                        showToast(resp.message)

                    }
                }
            }

            override fun onFailure(call: Call<DataResponse2>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun initUnlockedRV() {
        adapter = UnlockedFlowerpotRVAdapter(unlockedFpList)
        binding.unlockedFlowerpotRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.unlockedFlowerpotRv.adapter = adapter
        binding.unlockedFlowerpotRv.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        adapter.setOnItemClickListener(object : UnlockedFlowerpotRVAdapter.OnItemClickListener {
            override fun onItemClick(fp: UnlockedFpResult) {
                val intent = Intent(context, DetailUnlockedFpActivity::class.java)
                intent.putExtra("selectedFP", fp.idx)
                intent.putExtra("userFlowerListIdx", fp.userFlowerListIdx)
                startActivity(intent)
            }

        })


    }


    private fun getUserIdx(): Int {
        val spf = activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx", -1)
    }

}