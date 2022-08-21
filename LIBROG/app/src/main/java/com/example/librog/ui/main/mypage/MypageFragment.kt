package com.example.librog.ui.main.mypage

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.librog.ApplicationClass
import com.example.librog.R
import com.example.librog.data.local.AppDatabase

import com.example.librog.data.remote.data.*
import com.example.librog.data.remote.data.UserDataService
import com.example.librog.data.remote.data.UserStatResult

import com.example.librog.databinding.FragmentMypageBinding
import com.example.librog.ui.BaseFragment
import com.example.librog.ui.main.login.LoginActivity

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MypageFragment : BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate){
    lateinit var appDB: AppDatabase
    private val userDataService = UserDataService
    private val userService = ApplicationClass.retrofit.create(UserDataInterface::class.java)

    override fun initAfterBinding() {
        appDB = AppDatabase.getInstance(requireActivity())!!
        initViews()
        initClickListener()
        Toast.makeText(requireContext(), getIdx().toString(), Toast.LENGTH_SHORT).show()
    }

    private fun initClickListener(){
        binding.mypageLoginBtn.setOnClickListener {
            if (binding.mypageLoginBtn.text =="로그인"){
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
            else {
                logout()
                requireActivity().finish()
            }
        }

        binding.profileSettingBtn.setOnClickListener {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }

        binding.profileIv.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }
        binding.profileNameTv.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }
        binding.profileIntroTv.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getIdx(): Int{
        val spf = activity?.getSharedPreferences("userInfo",AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx",-1)
    }


    private fun initViews(){
        val id = getIdx()

        if (id==-1){ //기본값(로그아웃 상태)
            binding.mypageLoginBtn.text = "로그인"

        } else { //로그인 상태
            binding.mypageLoginBtn.text = "로그아웃"
        }

        //유저 프로필 불러오기
        if (id!=-1)
            getUserProfile(id)
        //유저 통계 불러오기
        userDataService.getUserStat(this,getIdx())
    }

    private fun logout(){
        val spf = activity?.getSharedPreferences("userInfo",AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("idx") //키값에 저장된값 삭제-> idx=-1
        editor.apply()

        binding.mypageLoginBtn.text = "로그인"
    }


    //유저 통계 표시 (UserDataService에서 호출)
    fun setData(result: UserStatResult) {
        binding.apply{
            mypageFlowerCnt.text = result.flowerCnt.toString()
            mypageReadingCnt.text = result.readingCnt.toString()
            mypageStarCnt.text = result.starRatingCnt.toString()
            mypageQuoteCnt.text = result.quoteCnt.toString()
            mypageContentCnt.text = result.contentCnt.toString()
        }
    }

    private fun getUserProfile(userIdx: Int){
        userService.getUserProfile(userIdx).enqueue(object: Callback<UserProfileResponse> {
            override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                val resp = response.body()!!
                setUserProfile(resp.result!!)
            }
            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
            }
        })
    }

    private fun setUserProfile(result: UserProfileResult){
        val imgUrl=appDB.userDao().getImgUrl(getEmail())
        if (imgUrl=="0"){
            binding.profileIv.setImageResource(R.drawable.ic_profile_logo)
        }
        else{
            val uri:Uri = Uri.parse(imgUrl)
            binding.profileIv.setImageURI(uri)
        }
        //Glide.with(this).load(result.profileImgUrl).circleCrop().into(binding.profileIv)
        binding.profileNameTv.text = result.name
        binding.profileIntroTv.text = result.introduction


        //로그인 상태 확인
        if (result.type=="kakao"){
            binding.kakaoLoginStatus.text = "연결완료"
            binding.kakaoLoginStatus.setTextColor(Color.parseColor("#64BE78"))
        }
        else {
            binding.appLoginStatus.text = "연결완료"
            binding.appLoginStatus.setTextColor(Color.parseColor("#64BE78"))
        }
    }

    private fun getEmail(): String{
        val spf = activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("email","0")!!
    }


}


