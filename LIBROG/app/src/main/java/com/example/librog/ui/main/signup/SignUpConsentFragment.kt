package com.example.librog.ui.main.signup

import android.graphics.Color
import android.view.View
import com.example.librog.R
import com.example.librog.databinding.FragmentSignupConsentBinding
import com.example.librog.ui.BaseFragment

class SignUpConsentFragment : BaseFragment<FragmentSignupConsentBinding>(FragmentSignupConsentBinding::inflate) {
    var isChecked1: Boolean = false
    var isChecked2: Boolean = false

    override fun initAfterBinding() {
        checkBtnClickListener()
        finishBtnClickListner()
    }

    private fun checkBtnClickListener(){
        binding.checkTopUnchecked.setOnClickListener {
            isChecked1=true
            binding.checkTopChecked.visibility = View.VISIBLE
            binding.checkTopUnchecked.visibility = View.INVISIBLE
            binding.checkTopTv1.setTextColor(Color.parseColor("#000000"))
            binding.checkTopTv2.setTextColor(Color.parseColor("#64BE78"))

            checkValidation()
        }

        binding.checkTopChecked.setOnClickListener {
            isChecked1=false
            binding.checkTopChecked.visibility = View.INVISIBLE
            binding.checkTopUnchecked.visibility = View.VISIBLE

            checkValidation()
        }

        binding.checkBottomUnchecked.setOnClickListener {
            isChecked2=true
            binding.checkBottomChecked.visibility = View.VISIBLE
            binding.checkBottomUnchecked.visibility = View.INVISIBLE
            binding.checkBottomTv1.setTextColor(Color.parseColor("#000000"))
            binding.checkBottomTv2.setTextColor(Color.parseColor("#64BE78"))

            checkValidation()
        }

        binding.checkBottomChecked.setOnClickListener {
            isChecked2=false
            binding.checkBottomChecked.visibility = View.INVISIBLE
            binding.checkBottomUnchecked.visibility = View.VISIBLE

            checkValidation()
        }

    }

    private fun checkValidation(){
        var isValid:Boolean =isChecked1&&isChecked2
        if (isValid){
            binding.suConsentWarningTv.visibility = View.INVISIBLE
            binding.finishInactiveBtn.visibility = View.INVISIBLE
            binding.finishActiveBtn.visibility = View.VISIBLE
        }
        else {
            binding.finishInactiveBtn.visibility = View.VISIBLE
            binding.finishActiveBtn.visibility = View.INVISIBLE
        }

    }

    private fun finishBtnClickListner(){
        binding.finishActiveBtn.setOnClickListener {
            changeFragment()
        }
        binding.finishInactiveBtn.setOnClickListener {
            if (!isChecked1){
                binding.checkTopTv1.setTextColor(Color.parseColor("#ff0000"))
                binding.checkTopTv2.setTextColor(Color.parseColor("#ff0000"))
            }
            if (!isChecked2){
                binding.checkBottomTv1.setTextColor(Color.parseColor("#ff0000"))
                binding.checkBottomTv2.setTextColor(Color.parseColor("#ff0000"))
            }

            binding.suConsentWarningTv.visibility = View.VISIBLE
        }
    }

    private fun changeFragment(){
        (context as SignUpActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.sign_up_frame, SignUpFirstFragment())
            .commitAllowingStateLoss()
    }





}