package com.example.librog.ui.main.addFlowerpot

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librog.ApplicationClass
import com.example.librog.data.remote.data.DataInterface
import com.example.librog.data.remote.data.DataResponse3
import com.example.librog.data.remote.data.LockedFpResult
import com.example.librog.data.remote.data.CheckUnlockedFpResponse
import com.example.librog.databinding.FragmentLockedFlowerpotBinding
import com.example.librog.ui.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class LockedFlowerpotFragment :
    BaseFragment<FragmentLockedFlowerpotBinding>(FragmentLockedFlowerpotBinding::inflate) {

    private var lockedFpList = ArrayList<LockedFpResult>()
    private val dataService = ApplicationClass.retrofit.create(DataInterface::class.java)
    private lateinit var adapter: LockedFlowerpotRVAdapter
    private lateinit var imm: InputMethodManager

    override fun initAfterBinding() {
        val userIdx = getUserIdx()
        lockedFpList.clear()
        imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        getLockedFpResult(userIdx)
        initLockedRv()
        initClickListener(userIdx)

    }

    private fun initClickListener(userIdx: Int) {
        binding.lockedFlowerpotSearchCl.setOnClickListener {
            binding.lockedFlowerpotSearchEt.requestFocus()
            imm.showSoftInput(binding.lockedFlowerpotSearchEt, InputMethodManager.SHOW_IMPLICIT)
        }


        binding.lockedFlowerpotCancelCl.setOnClickListener {
            binding.lockedFlowerpotSearchEt.text.clear()
            lockedFpList.clear()
            getLockedFpResult(userIdx)
        }


        binding.lockedFlowerpotSearchEt.setOnEditorActionListener { v, actionId, _ ->
            var handled = false

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                lockedFpList.clear()

                val input = binding.lockedFlowerpotSearchEt.text.toString()
                hideKeyboard(v)
                searchLockedFpResult(userIdx, input)
                handled = true
            }
            handled
        }

    }

    private fun searchLockedFpResult(userIdx: Int, name: String) {
        dataService.searchLockedFpResult(userIdx, name).enqueue(object : Callback<DataResponse3>{
            override fun onResponse(call: Call<DataResponse3>, response: Response<DataResponse3>) {
                val resp = response.body()!!

                when(resp.code){
                    1000 ->{
                        setLockedFpList(resp.result)
                    }
                    else ->{
                        showToast(resp.message)
                    }

                }
            }

            override fun onFailure(call: Call<DataResponse3>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }


    private fun hideKeyboard(v: View) {
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }



    // 미획득 화분 정보 가져오기 api
    private fun getLockedFpResult(userIdx: Int) {
        dataService.getLockedFpResult(userIdx).enqueue(object : Callback<DataResponse3> {
            override fun onResponse(call: Call<DataResponse3>, response: Response<DataResponse3>) {
                val resp = response.body()!!
                // 실패 시 코드 작성하기
                when (resp.code) {
                    1000 -> {
                        setLockedFpList(resp.result)
                    }
                    else ->{
                        showToast(resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<DataResponse3>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun initLockedRv() {
        adapter = LockedFlowerpotRVAdapter(lockedFpList)
        binding.lockedFlowerpotRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.lockedFlowerpotRv.adapter = adapter
        binding.lockedFlowerpotRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    fun setLockedFpList(result: ArrayList<LockedFpResult>) {
        lockedFpList.addAll(result)
        adapter.notifyDataSetChanged()
    }


    private fun getUserIdx(): Int{
        val spf = activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx",-1)
    }

}