package com.example.librog.ui.main.signup

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.librog.R
import com.example.librog.data.remote.data.auth.AuthService
import com.example.librog.data.remote.data.auth.SignUpInfo
import com.example.librog.data.remote.data.auth.SignUpView
import com.example.librog.databinding.FragmentSignupLastBinding
import com.example.librog.ui.BaseFragment

class SignUpLastFragment : BaseFragment<FragmentSignupLastBinding>(FragmentSignupLastBinding::inflate), SignUpView {
    lateinit var email:String
    lateinit var pwd:String
    lateinit var name: String
    lateinit var introduce: String
    lateinit var imgUri: Uri
    var isUriNull =true

    companion object {
        const val IMAGE_REQUEST_CODE = 100
    }
    override fun initAfterBinding() {
        initClickListener()
    }

    private fun initClickListener(){
        binding.suLastCameraIv.setOnClickListener {
            showBanner()
        }

        binding.imgOptionAlbumTv.setOnClickListener{
            hideBanner()
            pickImageGallery()
        }

        binding.imgOptionDefaultTv.setOnClickListener {
            binding.suProfileIv.setImageResource(R.drawable.ic_profile_logo)
            hideBanner()
        }

        binding.suLastFinishBtn.setOnClickListener {
            signUp()
        }
    }

    private fun signUp(){
        val authService = AuthService()
        authService.setSignUpView(this)
        Log.d("SIGNUP/",getSignUpInfo().toString())
        authService.signUp(getSignUpInfo()) //api호출
    }


    private fun getSignUpInfo():SignUpInfo {
        val spf = activity?.getSharedPreferences("signUp", AppCompatActivity.MODE_PRIVATE)

        email= spf!!.getString("email","0").toString()
        pwd = spf.getString("pwd","0").toString()
        name = spf.getString("name","0").toString()
        introduce = binding.suNicknameEt.text.toString()


        showToast(email)
        return SignUpInfo(email,pwd,name,"",introduce)
    }

    override fun onSignUpSuccess(message: String) {
        activity?.finish()
    }

    override fun onSignUpFailure(message: String) {
        showToast(message)
    }


    private fun showBanner(){
        binding.imgOptionBanner.visibility = View.VISIBLE
        binding.suLastFinishBtn.visibility = View.INVISIBLE
        (activity as SignUpActivity).fadeBackground(true)
        binding.suNicknameEt.isEnabled= false
    }

    private fun hideBanner(){
        binding.imgOptionBanner.visibility = View.INVISIBLE
        binding.suLastFinishBtn.visibility = View.VISIBLE
        (activity as SignUpActivity).fadeBackground(false)
        binding.suNicknameEt.isEnabled= true
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE) //인텐트를 통해 갤러리에 요청 코드 보냄
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data) //URI 객체로 이미지 전달받음
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            binding.suProfileIv.setImageURI(data?.data)

            imgUri = data?.data!!
            isUriNull=false
        }
    }


}