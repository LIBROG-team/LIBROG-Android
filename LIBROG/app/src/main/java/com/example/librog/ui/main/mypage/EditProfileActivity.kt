package com.example.librog.ui.main.mypage

import android.content.Intent
import android.graphics.Color
import android.view.View
import com.example.librog.R
import com.example.librog.databinding.ActivityEditProfileBinding

import com.example.librog.ui.BaseActivity
import com.example.librog.ui.main.signup.SignUpActivity
import com.example.librog.ui.main.signup.SignUpLastFragment

class EditProfileActivity: BaseActivity<ActivityEditProfileBinding>(ActivityEditProfileBinding::inflate) {
    override fun initAfterBinding() {
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
            hideBanner()
        }

        binding.profileEditFinishBtn.setOnClickListener {
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
    }

    private fun hideBanner(){
        binding.editImgOptionBanner.visibility = View.INVISIBLE
        binding.editGrayBg.visibility = View.INVISIBLE
        binding.profileEditFinishBtn.visibility = View.VISIBLE
        binding.editTitleLine.setBackgroundColor(Color.parseColor("#D9D9D9"))
        binding.editNicknameEt.isEnabled= true
        binding.editIntroduceEt.isEnabled= true
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, SignUpLastFragment.IMAGE_REQUEST_CODE) //인텐트를 통해 갤러리에 요청 코드 보냄
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data) //URI 객체로 이미지 전달받음
        if (requestCode == SignUpLastFragment.IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            binding.editProfileIv.setImageURI(data?.data)
            showToast(data?.data.toString())
        }
    }
}