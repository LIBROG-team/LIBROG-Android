package com.example.librog.ui.main.signup

import com.example.librog.databinding.FragmentSignupLastBinding
import com.example.librog.ui.BaseFragment


class SignUpLastFragment : BaseFragment<FragmentSignupLastBinding>(FragmentSignupLastBinding::inflate) {
    override fun initAfterBinding() {
        binding.suLastFinishBtn.setOnClickListener {
            activity?.finish()
        }
    }
}