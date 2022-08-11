package com.example.librog.ui.main.signup

import android.util.Log
import android.util.Patterns
import android.view.View
import com.example.librog.R
import com.example.librog.data.remote.data.auth.AuthService
import com.example.librog.data.remote.data.auth.SignUpInfo
import com.example.librog.data.remote.data.auth.SignUpView
import com.example.librog.databinding.FragmentSignupFirstBinding
import com.example.librog.ui.BaseFragment
import java.util.regex.Pattern

class SignUpFirstFragment : BaseFragment<FragmentSignupFirstBinding>(FragmentSignupFirstBinding::inflate),SignUpView {
    override fun initAfterBinding() {
        binding.suFirstNextBtn.setOnClickListener {
            signUp()
        }
    }


    private fun getSignUpInfo() : SignUpInfo {
        val email: String = binding.suIdEt.text.toString()
        val pwd: String = binding.suPwdEt.text.toString()
        val name: String = binding.suNameEt.text.toString()

        return SignUpInfo(email, pwd,name,"","")
    }

    private fun signUp(){
        validationCheck()
//        val authService = AuthService()
//        authService.setSignUpView(this)
//        Log.d("SIGNUP/",getSignUpInfo().toString())
//        authService.signUp(getSignUpInfo()) //api호출
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

    private fun validationCheck(){
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        var error="0"
        if (binding.suIdEt.text.toString().isEmpty()){
            error="이메일을 입력해주세요"
        }
        else if (!pattern.matcher(binding.suIdEt.text.toString()).matches()){
            error="이메일 형식을 정확하게 입력해주세요"
        }
        else if (binding.suIdEt.text.toString().length>30){
            error="이메일은 30자리 미만으로 입력해주세요"
        }
        else if (binding.suPwdEt.text.toString().isEmpty()){
            error="비밀번호를 입력해주세요"
        }
        else if (binding.suPwdEt.text.toString().length>21 || binding.suPwdEt.text.toString().length<7){
            error = "비밀번호는 8~20자리를 입력해주세요"
        }
        else if (binding.suNameEt.text.toString().isEmpty()){
            error="이름을 입력해주세요"
        }
        else if (binding.suNameEt.text.toString().length>21){
            error="이름은 20자보다 짧게 입력해주세요"
        }
        else{
            changeFragment()
        }

        showError(error)


    }

    private fun showError(error:String){
        binding.suErrorTv.visibility = View.VISIBLE
        binding.suErrorTv.text = error
    }
}