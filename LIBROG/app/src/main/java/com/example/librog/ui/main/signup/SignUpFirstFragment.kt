package com.example.librog.ui.main.signup

import android.os.Bundle
import com.example.librog.R
import com.example.librog.databinding.FragmentSignupFirstBinding
import com.example.librog.ui.BaseFragment
import com.example.librog.ui.main.MainActivity
import com.google.gson.Gson

class SignUpFirstFragment : BaseFragment<FragmentSignupFirstBinding>(FragmentSignupFirstBinding::inflate) {
    override fun initAfterBinding() {
        binding.signInNextBtn.setOnClickListener {
            changeFragment()
        }
    }

    private fun changeFragment(){
        (context as SignUpActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.sign_up_frame, SignUpLastFragment())
            .commitAllowingStateLoss()
    }
}