package com.example.librog.data.remote.data

import android.util.Log
import com.example.librog.ApplicationClass.Companion.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataService {
    fun getFpList(userIdx: Int) {
        val dataService = retrofit.create(DataInterface::class.java)

        dataService.getFpList(userIdx).enqueue(object : Callback<DataResponse1> {
            override fun onResponse(call: Call<DataResponse1>, response: Response<DataResponse1>) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> {
                        Log.d("resp", resp.result.toString())

                    }

                    2019 -> {

                    }
                    2020 -> {

                    }
                    else -> {

                    }
                }

            }

            override fun onFailure(call: Call<DataResponse1>, t: Throwable) {
            }
        })

    }

}