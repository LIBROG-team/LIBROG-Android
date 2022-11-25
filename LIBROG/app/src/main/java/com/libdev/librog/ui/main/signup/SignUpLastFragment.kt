package com.libdev.librog.ui.main.signup

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.libdev.librog.R
import com.libdev.librog.data.local.AppDatabase
import com.libdev.librog.data.remote.data.auth.AuthService
import com.libdev.librog.data.remote.data.auth.SignUpInfo
import com.libdev.librog.data.remote.data.auth.SignUpView
import com.libdev.librog.databinding.FragmentSignupLastBinding
import com.libdev.librog.ui.BaseFragment

class SignUpLastFragment : BaseFragment<FragmentSignupLastBinding>(FragmentSignupLastBinding::inflate), SignUpView {
    lateinit var email:String
    lateinit var pwd:String
    lateinit var name: String
    lateinit var introduce: String
    lateinit var imgUri: Uri
    lateinit var appDB: AppDatabase
    var isImgNull =true

    companion object {
        const val IMAGE_REQUEST_CODE = 100
    }
    override fun initAfterBinding() {
        appDB = AppDatabase.getInstance(requireActivity())!!
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
            isImgNull=true
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

        return SignUpInfo(email,pwd,name,"",introduce)
    }

    override fun onSignUpSuccess(message: String) {
        saveEmail()
        if (!isImgNull)
            appDB.userDao().insertImgUrl(email,imgUri.toString())
        else
            appDB.userDao().insertImgUrl(email,"0")
        showToast("회원가입에 성공하셨습니다");
        activity?.finish()
    }

    override fun onSignUpFailure(message: String) {
        android.app.AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("확인") { _, _ ->

            }
            .show()
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
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE) //인텐트를 통해 갤러리에 요청 코드 보냄
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data) //URI 객체로 이미지 전달받음
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            binding.suProfileIv.setImageURI(data?.data)

            isImgNull=false
            imgUri = data?.data!!
            val contentResolver = requireActivity().applicationContext.contentResolver
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION

            imgUri.let { contentResolver.takePersistableUriPermission(it, takeFlags)
            }
        }}

    private fun saveEmail(){
        val spf = requireActivity().getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("email",email)
        editor.apply()
    }


}