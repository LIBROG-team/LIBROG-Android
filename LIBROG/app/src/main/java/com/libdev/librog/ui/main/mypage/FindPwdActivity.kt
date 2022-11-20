package com.libdev.librog.ui.main.mypage

import android.util.Log
import android.view.View
import com.libdev.librog.ApplicationClass
import com.libdev.librog.data.remote.data.auth.AuthRetrofitInterface
import com.libdev.librog.data.remote.data.auth.FindPwdInfo
import com.libdev.librog.data.remote.data.auth.FindPwdResponse
import com.libdev.librog.databinding.ActivityFindPwdBinding
import com.libdev.librog.ui.BaseActivity
import com.libdev.librog.ui.main.splash.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindPwdActivity: BaseActivity<ActivityFindPwdBinding>(ActivityFindPwdBinding::inflate) {
    private val service = ApplicationClass.retrofit.create(AuthRetrofitInterface::class.java)

    override fun initAfterBinding() {
        binding.findPwdBackBtn.setOnClickListener {
            finish()
        }
        binding.findPwdFinishBtn.setOnClickListener {
            findPwd(getFindPwdInfo())
            Log.d("findPwd/start","이메일 전송 버튼 누름")
        }
    }

    private fun getFindPwdInfo(): FindPwdInfo{
        val email = binding.findPwdEmailEt.text.toString()
        return FindPwdInfo(email)
    }

    private fun findPwd(findPwdInfo: FindPwdInfo){
        service.findPwd(findPwdInfo).enqueue(object: Callback<FindPwdResponse> {
            override fun onResponse(call: Call<FindPwdResponse>, response: Response<FindPwdResponse>) {
                Log.d("findPwd/in","response함수 내부")
                val resp = response.body()!!
                when (resp.code){
                    1000->{
                        setResult("해당 이메일로 임시 비밀번호를 전송했습니다.",true)
                    }

                    else->{
                        setResult(resp.message,false)
                    }
                }
            }
            override fun onFailure(call: Call<FindPwdResponse>, t: Throwable) {
            }
        })
    }

    private fun setResult(message:String,isSuccess:Boolean){
        android.app.AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("확인") { _, _ ->
                if (isSuccess){
                    finish()
                }
            }
            .show()
    }
}