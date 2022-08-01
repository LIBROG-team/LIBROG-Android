package com.example.librog.ui.main.signup

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import com.example.librog.R
import com.example.librog.databinding.FragmentSignupConsentBinding
import com.example.librog.databinding.FragmentSignupFirstBinding
import com.example.librog.ui.BaseFragment

class SignUpConsentFragment : BaseFragment<FragmentSignupConsentBinding>(FragmentSignupConsentBinding::inflate) {
    override fun initAfterBinding() {
        binding.suConsentFinishBtn2.setOnClickListener {
            changeFragment()
        }
    }

    private fun changeFragment(){
        (context as SignUpActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.sign_up_frame, SignUpFirstFragment())
            .commitAllowingStateLoss()
    }


}