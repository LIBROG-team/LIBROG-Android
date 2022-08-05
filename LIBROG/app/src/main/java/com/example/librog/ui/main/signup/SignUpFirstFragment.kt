package com.example.librog.ui.main.signup

import com.example.librog.R
import com.example.librog.data.remote.data.auth.AuthService
import com.example.librog.data.remote.data.auth.SignUpInfo
import com.example.librog.data.remote.data.auth.SignUpView
import com.example.librog.databinding.FragmentSignupFirstBinding
import com.example.librog.ui.BaseFragment

class SignUpFirstFragment : BaseFragment<FragmentSignupFirstBinding>(FragmentSignupFirstBinding::inflate),SignUpView {
    override fun initAfterBinding() {
        binding.suFirstNextBtn.setOnClickListener {
            changeFragment()
        }
    }


    private fun getSignUpInfo() : SignUpInfo {
        val email: String = binding.suIdEt.text.toString()
        val pwd: String = binding.suPwdEt.text.toString()
        val name: String = binding.suNameEt.text.toString()

        return SignUpInfo(email, pwd,name)
    }




    private fun signUp(){

        val authService = AuthService()
        authService.setSignUpView(this)
        showToast(getSignUpInfo().toString())
        authService.signUp(getSignUpInfo()) //api호출
    }

    override fun onSignUpSuccess(message: String) {
        changeFragment()
    }

    override fun onSignUpFailure(message: String) {
        showToast(message)
    }

    private fun changeFragment(){
        (context as SignUpActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.sign_up_frame, SignUpLastFragment())
            .commitAllowingStateLoss()
    }
}