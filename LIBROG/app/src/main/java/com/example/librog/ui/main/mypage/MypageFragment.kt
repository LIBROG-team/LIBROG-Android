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
import com.example.librog.data.local.AppDatabase
import com.example.librog.databinding.FragmentMypageBinding
import com.example.librog.ui.main.MainActivity
import com.example.librog.ui.main.login.LoginActivity


class MypageFragment : Fragment(){
    lateinit var binding: FragmentMypageBinding
    lateinit var AppDB: AppDatabase
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

    private fun getJwt(): String?{
        val spf = activity?.getSharedPreferences("auth2",AppCompatActivity.MODE_PRIVATE) //fragment->?추가
        return spf!!.getString("jwt","0") //기본값 0
    }

    private fun getIdx(): Int{
        val spf = activity?.getSharedPreferences("userInfo",AppCompatActivity.MODE_PRIVATE) //fragment->?추가
        return spf!!.getInt("idx",-1)
    }


    private fun initViews(){
        val id = getIdx()
        Log.d("getidx",id.toString())
        if (id==-1){ //기본값(로그인x)
            binding.mypageLoginBtn.text = "로그인"
            binding.mypageLoginBtn.setOnClickListener {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        } else {
            binding.mypageLoginBtn.text = "로그아웃"
            binding.profileName.text=AppDB.userDao().getUserName(id)
            binding.mypageLoginBtn.setOnClickListener {
                logout()
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun logout(){
        val spf = activity?.getSharedPreferences("userInfo",AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("idx") //키값에 저장된값 삭제
        editor.apply()
        binding.mypageLoginBtn.text = "로그인"
    }



}