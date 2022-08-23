package com.example.librog.ui.main.mypage

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.librog.ApplicationClass
import com.example.librog.R
import com.example.librog.data.local.AppDatabase
import com.example.librog.data.remote.data.*
import com.example.librog.databinding.ActivityEditProfileBinding

import com.example.librog.ui.BaseActivity
import com.example.librog.ui.main.signup.SignUpLastFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditProfileActivity: BaseActivity<ActivityEditProfileBinding>(ActivityEditProfileBinding::inflate) {
    private lateinit var imgUri: Uri
    private var isImgNull = true
    lateinit var appDB: AppDatabase
    private val service = ApplicationClass.retrofit.create(UserDataInterface::class.java)

    override fun initAfterBinding() {
        appDB = AppDatabase.getInstance(this)!!
        Log.d("img",isImgNull.toString())
        getUserProfile(getIdx())
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
            isImgNull=true
            binding.editProfileIv.setImageResource(R.drawable.ic_profile_logo)
            hideBanner()
        }

        binding.profileEditFinishBtn.setOnClickListener {
            if (!isImgNull)
                appDB.userDao().updateImgUrl(getEmail(),imgUri.toString())
            else {
                setDefaultImg()
                appDB.userDao().updateImgUrl(getEmail(),"0")
            }//기본이미지로 설정
            Log.d("img",isImgNull.toString())
            editProfile(getIntroduceInfo()) //자기소개 수정 api 호출
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

            isImgNull=false
            imgUri = data?.data!!
            val contentResolver = applicationContext.contentResolver
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION

            imgUri.let { contentResolver.takePersistableUriPermission(it, takeFlags)
            }
    }}

    private fun editProfile(editProfileInfo: EditProfileInfo){
        service.editProfile(editProfileInfo).enqueue(object: Callback<EditProfileResponse> {
            override fun onResponse(call: Call<EditProfileResponse>, response: Response<EditProfileResponse>) {
                val resp = response.body()!!
                when (resp.code){
                    1000->{
                        Log.d("editIntro/success",resp.message)
                        finish()
                    }
                    2004->{
                        showToast(resp.message)
                        Log.d("editIntro/fail",resp.message)
                    }
                }
            }
            override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
            }
        })
    }

    private fun getIntroduceInfo(): EditProfileInfo{
        val id=getIdx()
        val name = binding.editNicknameEt.text.toString()
        val introduction= binding.editIntroduceEt.text.toString()

        return EditProfileInfo(id,name,introduction,"")
    }

    private fun getIdx(): Int{
        val spf = getSharedPreferences("userInfo",MODE_PRIVATE)
        return spf!!.getInt("idx",-1)
    }

    private fun getUserProfile(userIdx: Int){
        service.getUserProfile(userIdx).enqueue(object: Callback<UserProfileResponse> {
            override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                val resp = response.body()!!
                initProfile(resp.result!!)
            }
            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
            }
        })
    }

    private fun initProfile(result: UserProfileResult){
        val imgUrl=appDB.userDao().getImgUrl(getEmail())
        
        binding.editIntroduceEt.setText(result.introduction)
        binding.editNicknameEt.setText(result.name)
        if (imgUrl=="0"||imgUrl=="1"){
            binding.editProfileIv.setImageResource(R.drawable.ic_profile_logo)
        }
//        else if (imgUrl=="1"){ //유저가 이미지를 수정하지 않을 시 카카오 계정 이미지
//            Glide.with(this).load(result.profileImgUrl).circleCrop().into(binding.editProfileIv)
//        }
        else{
            val uri:Uri = Uri.parse(imgUrl)
            binding.editProfileIv.setImageURI(uri)
        }

    }
    private fun getEmail(): String{
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getString("email","0")!!
    }

    private fun setDefaultImg(){
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("imgUri")
        editor.apply()
    }

}