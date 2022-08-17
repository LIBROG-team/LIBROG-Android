package com.example.librog.ui.main.mypage

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.librog.R
import com.example.librog.data.local.AppDatabase
import com.example.librog.databinding.ActivityEditProfileBinding

import com.example.librog.ui.BaseActivity
import com.example.librog.ui.main.signup.SignUpActivity
import com.example.librog.ui.main.signup.SignUpLastFragment

class EditProfileActivity: BaseActivity<ActivityEditProfileBinding>(ActivityEditProfileBinding::inflate) {
    private lateinit var imgUri: Uri
    private var isImgNull = true
    override fun initAfterBinding() {
//        if(getImgUri()!="0"){
//            val uri:Uri = Uri.parse(getImgUri())
//            binding.editProfileIv.setImageURI(uri)
//            showToast(getImgUri())
//        }
        initClickListener()
    }

    private fun initClickListener(){
        binding.editCameraIv.setOnClickListener {
            showBanner()
        }

        binding.editImgOptionAlbumTv.setOnClickListener{
            hideBanner()
            pickImageGallery()
        }

        binding.editImgOptionDefaultTv.setOnClickListener {
            binding.editProfileIv.setImageResource(R.drawable.ic_profile_logo)
            isImgNull=true
            hideBanner()
        }

        binding.profileEditFinishBtn.setOnClickListener {
//            if (!isImgNull)
//                saveUri(imgUri)
            finish()
        }

        binding.editBackBtn.setOnClickListener {
            finish()
        }
    }

    private fun showBanner(){
        binding.editImgOptionBanner.visibility = View.VISIBLE
        binding.editGrayBg.visibility = View.VISIBLE
        binding.profileEditFinishBtn.visibility = View.INVISIBLE
        binding.editTitleLine.setBackgroundColor(Color.parseColor("#8F8F8F"))
        binding.editNicknameEt.isEnabled= false
        binding.editIntroduceEt.isEnabled= false
        binding.editBackBtn.isEnabled = false
    }

    private fun hideBanner(){
        binding.editImgOptionBanner.visibility = View.INVISIBLE
        binding.editGrayBg.visibility = View.INVISIBLE
        binding.profileEditFinishBtn.visibility = View.VISIBLE
        binding.editTitleLine.setBackgroundColor(Color.parseColor("#D9D9D9"))
        binding.editNicknameEt.isEnabled= true
        binding.editIntroduceEt.isEnabled= true
        binding.editBackBtn.isEnabled = true
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, SignUpLastFragment.IMAGE_REQUEST_CODE) //인텐트를 통해 갤러리에 요청 코드 보냄
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data) //URI 객체로 이미지 전달받음
        if (requestCode == SignUpLastFragment.IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            binding.editProfileIv.setImageURI(data?.data)
            showToast(data?.data.toString())
            isImgNull=false
            imgUri = data?.data!!

            val contentResolver = applicationContext.contentResolver
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION

            imgUri.let { contentResolver.takePersistableUriPermission(it, takeFlags)
            }
    }}

//    private fun saveUri(imageUri:Uri){
//        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
//        val editor = spf.edit()
//
//        editor.putString("imgUri",imageUri.toString())
//        editor.apply()
//    }
//
//    private fun getImgUri(): String{
//        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
//        return spf!!.getString("imgUri","0")!!
//    }
}