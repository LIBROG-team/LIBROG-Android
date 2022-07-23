package com.example.librog.ui.main.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.librog.R
import com.example.librog.data.HistoryBookData
import com.example.librog.databinding.FragmentHistoryBinding
import com.example.librog.databinding.FragmentMypageBinding
import com.example.librog.ui.BaseFragment
import com.example.librog.ui.main.MainActivity
import com.example.librog.ui.main.login.LoginActivity


class MypageFragment : Fragment() {
    lateinit var binding: FragmentMypageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        initViews()

        return binding.root
    }


    private fun getJwt(): Int{
        val spf = activity?.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE) //fragment->?추가
        return spf!!.getInt("jwt",0) //기본값 0
    }

    private fun initViews(){
        val jwt : Int = getJwt()

        if (jwt==0){ //기본값(로그인x)
            binding.mypageLoginBtn.text = "로그인"
            binding.mypageLoginBtn.setOnClickListener {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        } else {
            binding.mypageLoginBtn.text = "로그아웃"
            binding.mypageLoginBtn.setOnClickListener {
                val intent = Intent(activity, MainActivity::class.java)
                logout()
                startActivity(intent)
            }
        }
    }

    private fun logout(){
        val spf = activity?.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("jwt") //키값에 저장된값 삭제
        editor.apply()
    }

}