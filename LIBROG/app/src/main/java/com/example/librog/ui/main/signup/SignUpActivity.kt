package com.example.librog.ui.main.signup

import android.util.Log
import android.widget.Toast
import com.example.librog.data.entities.User
import com.example.librog.data.local.UserDatabase
import com.example.librog.databinding.ActivityLoginBinding
import com.example.librog.databinding.ActivitySignupBinding
import com.example.librog.ui.BaseActivity
import kotlin.math.sign

class SignUpActivity: BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate) {
    override fun initAfterBinding() {

        binding.signUpSignUpBtn.setOnClickListener {
            signUp()
            finish()
        }
    }

    private fun getUser(): User {
        val email: String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd: String = binding.signUpPasswordEt.text.toString()

        return User(email, pwd)
    }

    private fun signUp(){
        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }



        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val userDB = UserDatabase.getInstance(this)!!
        userDB.TempUserDao().insert(getUser())

        val user = userDB.TempUserDao().getUsers()
        Log.d("SIGNUPACT", user.toString())
}
}