package com.example.librog.ui.main.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.librog.data.local.AppDatabase
import com.example.librog.data.remote.data.DataService
import com.example.librog.data.remote.data.UserDataService
import com.example.librog.data.remote.data.UserStatResult
import com.example.librog.databinding.FragmentMypageBinding
import com.example.librog.ui.main.MainActivity
import com.example.librog.ui.main.login.LoginActivity


class MypageFragment : Fragment(){
    lateinit var binding: FragmentMypageBinding
    lateinit var AppDB: AppDatabase
    private val userDataService = UserDataService
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        AppDB =AppDatabase.getInstance(requireContext())!!
        initViews()
        Toast.makeText(requireContext(), getIdx().toString(), Toast.LENGTH_SHORT).show()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun getIdx(): Int{
        val spf = activity?.getSharedPreferences("userInfo",AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("idx",-1)
    }


    private fun initViews(){
        val id = getIdx()

        if (id==-1){ //기본값(로그아웃 상태)
            binding.mypageLoginBtn.text = "로그인"
            binding.mypageLoginBtn.setOnClickListener {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        } else { //로그인 상태
            binding.mypageLoginBtn.text = "로그아웃"
            binding.profileName.text=AppDB.userDao().getUserName(id)
            Glide.with(this).load(AppDB.userDao().getUserImg(id)).circleCrop().into(binding.profileIv)
            binding.mypageLoginBtn.setOnClickListener {
                logout()
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
        }

        //유저 통계 불러오기

        userDataService.getUserStat(this)
    }

    private fun logout(){
        val spf = activity?.getSharedPreferences("userInfo",AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("idx") //키값에 저장된값 삭제-> idx=-1
        editor.apply()
        binding.mypageLoginBtn.text = "로그인"
    }

    fun setData(result: UserStatResult, code:Int) {
        Log.d("GETUSERDATA",code.toString())
        binding.mypageFlowerCnt.text = result.flowerCnt.toString()
        binding.mypageReadingCnt.text = result.readingCnt.toString()
        binding.mypageStarCnt.text = result.starRatingCnt.toString()
        binding.mypageQuoteCnt.text = result.quoteCnt.toString()
        binding.mypageContentCnt.text = result.contentCnt.toString()
    }


}