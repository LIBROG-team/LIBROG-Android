package com.example.librog.ui.main.mypage

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import com.example.librog.ApplicationClass
import com.example.librog.data.remote.data.auth.AuthRetrofitInterface
import com.example.librog.data.remote.data.auth.DeleteUserResponse
import com.example.librog.databinding.ActivitySettingBinding
import com.example.librog.ui.BaseActivity
import com.example.librog.ui.main.splash.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingActivity: BaseActivity<ActivitySettingBinding>(ActivitySettingBinding::inflate) {
    private val service = ApplicationClass.retrofit.create(AuthRetrofitInterface::class.java)

    override fun initAfterBinding() {
        binding.settingBackBtn.setOnClickListener {
            finish()
        }
        //프로필 변경
        binding.settingProfileBtn.setOnClickListener {
            startNextActivity(EditProfileActivity::class.java)
        }

        //비밀번호 변경
        binding.settingChangePwdBtn.setOnClickListener {
            startNextActivity(ChangePwdActivity::class.java)
        }
        leavePanelClickListener()

        //홈페이지 방문
        binding.settingHomepageBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://blog.naver.com/librog"))
            startActivity(intent)
        }
        //홈페이지 방문
        binding.settingHomepageBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://blog.naver.com/librog"))
            startActivity(intent)
        }
        //개인정보취급방침
        binding.settingPrivateBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://librog.shop/blog/privacypolicy.html"))
            startActivity(intent)
        }
        //쿠폰입력
        binding.settingAddCouponBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://librog.shop/blog/promotion.html"))
            startActivity(intent)
        }

    }

    private fun leavePanelClickListener(){
        //탈퇴하기
        binding.settingLeaveBtn.setOnClickListener{
            binding.leaveConfirmPanel.visibility = View.VISIBLE
        }
        //탈퇴 확인
        binding.leaveOkayBtn.setOnClickListener {
            deleteUser()
        }
        //탈퇴 취소
        binding.leaveCancelBtn.setOnClickListener {
            binding.leaveConfirmPanel.visibility=View.INVISIBLE
        }
        //탈퇴 완료
        binding.leaveFinishBtn.setOnClickListener {
            startNextActivity(SplashActivity::class.java)
        }
    }

    private fun deleteUser(){
        service.deleteUser(getIdx()).enqueue(object: Callback<DeleteUserResponse> {
            override fun onResponse(call: Call<DeleteUserResponse>, response: Response<DeleteUserResponse>) {
                Log.d("findPwd/in","response함수 내부")
                val resp = response.body()!!
                when (resp.code){
                    1000->{
                        binding.leaveConfirmPanel.visibility=View.INVISIBLE
                        binding.leaveFinishPanel.visibility=View.VISIBLE
                    }

                }
            }
            override fun onFailure(call: Call<DeleteUserResponse>, t: Throwable) {
            }
        })
    }

    private fun getIdx(): Int{
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("idx",-1)
    }
}