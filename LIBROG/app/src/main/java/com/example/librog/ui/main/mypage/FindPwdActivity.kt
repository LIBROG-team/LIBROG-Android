package com.example.librog.ui.main.mypage

import android.util.Log
import android.view.View
import com.example.librog.ApplicationClass
import com.example.librog.data.remote.data.auth.AuthRetrofitInterface
import com.example.librog.data.remote.data.auth.FindPwdInfo
import com.example.librog.data.remote.data.auth.FindPwdResponse
import com.example.librog.databinding.ActivityFindPwdBinding
import com.example.librog.ui.BaseActivity
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
        binding.findPwdPanelBtn.setOnClickListener {
            setClickable(true)
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
        setClickable(false)
        binding.findPwdPanel.visibility = View.VISIBLE
        binding.findPwdPanelTv.text = message
        when (isSuccess){
            true->{
                binding.findPwdPanelBtn.setOnClickListener {
                    finish()
                }
            }
            false->{
                binding.findPwdPanelBtn.setOnClickListener {
                    binding.findPwdPanel.visibility = View.INVISIBLE
                    setClickable(true)
                }
            }
        }
    }

    private fun setClickable(isClickable: Boolean){
        when (isClickable){
            true->binding.findPwdDisableArea.visibility= View.INVISIBLE
            false->binding.findPwdDisableArea.visibility= View.VISIBLE
        }
        binding.findPwdBackBtn.isClickable=isClickable
        binding.findPwdEmailEt.isClickable=isClickable
    }
}