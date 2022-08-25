package com.example.librog.ui.main.mypage

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.librog.ApplicationClass
import com.example.librog.data.local.AppDatabase
import com.example.librog.data.remote.data.auth.AuthRetrofitInterface
import com.example.librog.data.remote.data.auth.DeleteUserResponse
import com.example.librog.databinding.ActivitySettingBinding
import com.example.librog.ui.BaseActivity
import com.example.librog.ui.main.splash.SplashActivity
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingActivity: BaseActivity<ActivitySettingBinding>(ActivitySettingBinding::inflate) {
    private val service = ApplicationClass.retrofit.create(AuthRetrofitInterface::class.java)
    lateinit var appDB: AppDatabase
    override fun initAfterBinding() {
        appDB = AppDatabase.getInstance(this)!!
        binding.settingBackBtn.setOnClickListener {
            finish()
        }
        //프로필 변경
        binding.settingProfileArea.setOnClickListener {
            startNextActivity(EditProfileActivity::class.java)
        }

        //비밀번호 변경
        binding.settingChangePwdArea.setOnClickListener {
            when (getLoginType()){
                "kakao"->{
                    binding.changedPwdDeniedPanel.visibility = View.VISIBLE
                    setClickable(false)
                }
                else->startNextActivity(ChangePwdActivity::class.java)
            }
        }

        binding.changePwdDeniedBtn.setOnClickListener {
            binding.changedPwdDeniedPanel.visibility = View.INVISIBLE
            setClickable(true)
        }
        leavePanelClickListener()

        //홈페이지 방문
        binding.settingHomepageArea.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://blog.naver.com/librog"))
            startActivity(intent)
        }
        //개인정보취급방침
        binding.settingPrivateArea.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://librog.shop/blog/privacypolicy.html"))
            startActivity(intent)
        }
        //문의하기
        binding.settingAskArea.setOnClickListener {
            showToast("librogmaster@gmail.com")
        }
        //쿠폰입력
        binding.settingCouponArea.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://librog.shop/blog/promotion.html"))
            startActivity(intent)
        }

    }

    private fun leavePanelClickListener(){
        //탈퇴하기
        binding.settingLeaveArea.setOnClickListener{
            binding.leaveConfirmPanel.visibility = View.VISIBLE
            setClickable(false)
        }
        //탈퇴 확인
        binding.leaveOkayBtn.setOnClickListener {
            deleteUser()
        }
        //탈퇴 취소
        binding.leaveCancelBtn.setOnClickListener {
            binding.leaveConfirmPanel.visibility=View.INVISIBLE
            setClickable(true)
        }
        //탈퇴 완료
        binding.leaveFinishBtn.setOnClickListener {
            appDB.userDao().deleteUser(getEmail())
            removeIdx()
            startNextActivity(SplashActivity::class.java)
        }
    }

    private fun setClickable(isClickable: Boolean){
        when (isClickable){
            true->binding.settingDisableArea.visibility=View.INVISIBLE
            false->binding.settingDisableArea.visibility=View.VISIBLE
        }
        binding.settingProfileArea.isClickable=isClickable
        binding.settingHomepageArea.isClickable=isClickable
        binding.settingPrivateArea.isClickable=isClickable
        binding.settingAskArea.isClickable=isClickable
        binding.settingChangePwdArea.isClickable=isClickable
        binding.settingCouponArea.isClickable=isClickable
        binding.settingLeaveArea.isClickable=isClickable
        binding.settingBackBtn.isClickable=isClickable
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
                        if (getLoginType()=="kakao"){
                            kakaoLogout()
                        }
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

    private fun getEmail(): String{
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getString("email","0")!!
    }

    private fun removeIdx(){
        val spf = getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("idx") //키값에 저장된값 삭제-> idx=-1
        editor.apply()
    }

    fun kakaoLogout(){
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("kakaoLogout", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i("kakaoLogout", "로그아웃 성공. SDK에서 토큰 삭제됨")
            }

        }

        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e("kakaoLogout", "연결 끊기 실패", error)
            }
            else {
                Log.i("kakaoLogout", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

    private fun getLoginType(): String{
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getString("type","0")!!
    }
}