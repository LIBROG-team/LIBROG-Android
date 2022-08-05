package com.example.librog.ui.main.signup

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.getSystemService
import com.example.librog.databinding.ActivitySignupBinding
import com.example.librog.databinding.FragmentHomeBinding
import com.example.librog.databinding.FragmentSignupLastBinding
import com.example.librog.ui.BaseFragment


class SignUpLastFragment : BaseFragment<FragmentSignupLastBinding>(FragmentSignupLastBinding::inflate) {
    lateinit var binding2: ActivitySignupBinding

    companion object {
        const val IMAGE_REQUEST_CODE = 100
    }
    override fun initAfterBinding() {

        binding.suLastFinishBtn.setOnClickListener {
            activity?.finish()
        }

        binding.suLastCameraIv.setOnClickListener {
            showBanner()
        }

        binding.imgOptionAlbumTv.setOnClickListener{
            pickImageGallery()
            hideBanner()
        }

        binding2 = ActivitySignupBinding.inflate(layoutInflater)


    }

    private fun showBanner(){
        binding.imgOptionBanner.visibility = View.VISIBLE
        binding.suLastFinishBtn.visibility = View.INVISIBLE

    }

    private fun hideBanner(){
        binding.imgOptionBanner.visibility = View.INVISIBLE
        binding.suLastFinishBtn.visibility = View.VISIBLE

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
            showToast(data?.data.toString())
        }
    }






}