package com.example.librog.ui.main.mypage

import android.util.Log
import com.example.librog.ApplicationClass
import com.example.librog.data.remote.data.auth.*
import com.example.librog.databinding.ActivityChangePwdBinding
import com.example.librog.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePwdActivity: BaseActivity<ActivityChangePwdBinding>(ActivityChangePwdBinding::inflate) {
    private val service = ApplicationClass.retrofit.create(AuthRetrofitInterface::class.java)
    override fun initAfterBinding(){
        binding.changePwdBackBtn.setOnClickListener {
            finish()
        }
        binding.changePwdFinishBtn.setOnClickListener {
            changePwd()
        }
    }

    private fun changePwd(){
        service.changePwd(getToken(),getChangePwdInfo()).enqueue(object: Callback<ChangePwdResponse> {
            override fun onResponse(call: Call<ChangePwdResponse>, response: Response<ChangePwdResponse>) {
                Log.d("changePwd/in","response함수 내부")
                val resp = response.body()!!
                when (resp.code){
                    1000->{
                        showToast("비밀번호가 성공적으로 변경되었습니다.")
                        finish()
                    }
                    else->{
                        showToast(resp.message)
                    }

                }
            }
            override fun onFailure(call: Call<ChangePwdResponse>, t: Throwable) {
            }
        })
    }

    private fun getIdx(): Int{
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("idx",-1)
    }

    private fun getToken(): String{
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getString("token","-1")!!
    }

    private fun getChangePwdInfo() : ChangePwdInfo {
        val idx= getIdx()
        val oldPwd: String = binding.changeOriginPwdEt.text.toString()
        val newPwd: String = binding.changeNewPwdEt.text.toString()

        return ChangePwdInfo(idx, oldPwd, newPwd)
    }
}