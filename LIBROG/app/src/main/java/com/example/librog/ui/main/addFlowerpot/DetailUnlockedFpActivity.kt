package com.example.librog.ui.main.addFlowerpot

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.librog.ApplicationClass
import com.example.librog.data.remote.data.*
import com.example.librog.databinding.ActivityDetailUnlockedFpBinding
import com.example.librog.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUnlockedFpActivity : BaseActivity<ActivityDetailUnlockedFpBinding>(ActivityDetailUnlockedFpBinding::inflate) {

    private val dataService = ApplicationClass.retrofit.create(DataInterface::class.java)


    override fun initAfterBinding() {
        val userIdx = getUserIdx()
        val fpIdx = intent.getIntExtra("selectedFP", -1)
        val userFlowerListIdx = intent.getIntExtra("userFlowerListIdx", -1)
        if (fpIdx == -1 || userFlowerListIdx == -1){
            Log.e("Error", "화분 상세 오류")
            finish()
        }

        initClickListener(userIdx, userFlowerListIdx)
        getDetailFp(fpIdx)

    }

    private fun initClickListener(userIdx: Int, userFlowerListIdx: Int) {
        binding.detailUnlockedBackBtnIv.setOnClickListener {
            finish()
        }
        binding.detailUnlockedSelectIv.setOnClickListener {
            addUserFlowerpot(userFlowerListIdx)
        }

    }

    private fun getDetailFp(flowerDataIdx: Int){
        dataService.getDetailFp(flowerDataIdx).enqueue(object : Callback<DataResponse4> {
            override fun onResponse(call: Call<DataResponse4>, response: Response<DataResponse4>) {
                val resp = response.body()!!
                when(resp.code){
                    1000 ->{
                        bind(resp.result)
                    }
                    else ->{
                        showToast(resp.message)
                    }

                }


            }

            override fun onFailure(call: Call<DataResponse4>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun addUserFlowerpot(userFlowerListIdx: Int){
        dataService.addFlowerpot(userFlowerListIdx).enqueue(object : Callback<AddFpResponse>{
            override fun onResponse(
                call: Call<AddFpResponse>,
                response: Response<AddFpResponse>
            ) {
                val resp = response.body()!!
                when(resp.code){
                    1000 ->{
                        showToast("화분 추가에 성공하였습니다.")
                        finish()
                    }
                    else ->{
                        Log.d("addUserFlowerpot", resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<AddFpResponse>, t: Throwable) {
                Log.e("addUserFlowerpot",t.message.toString())
            }

        })
    }

    private fun bind(result: ArrayList<DetailFpResult>){

        for (item in result){
            binding.apply {
                detailUnlockedNameTv.text = item.name
                detailUnlockedEngNameTv.text = item.engName
                detailUnlockedContentTv.text= item.content ?: "화분 설명"
                Glide.with(applicationContext)
                    .load(item.flowerImgUrl)
                    .into(detailUnlockedImgIv)


            }
        }

    }



    private fun getUserIdx(): Int{
        val spf = getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx",-1)
    }

}