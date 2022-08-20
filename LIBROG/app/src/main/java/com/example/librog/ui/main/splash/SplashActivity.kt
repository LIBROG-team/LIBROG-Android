package com.example.librog.ui.main.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.librog.databinding.ActivitySplashBinding
import com.example.librog.ui.BaseActivity
import com.example.librog.ui.main.MainActivity
import com.example.librog.ui.main.login.LoginActivity

class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate), SplashView {

    override fun initAfterBinding() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            alreadyLogin()
//            autoLogin()
        }, 2000)
    }

    private fun autoLogin() {
//        AuthService.autoLogin(this)
    }

    override fun onAutoLoginLoading() {

    }

    override fun onAutoLoginSuccess() {
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onAutoLoginFailure(code: Int, message: String) {
//        startActivityWithClear(LoginActivity::class.java)
    }

    private fun alreadyLogin(){
        if (getIdx()!=-1){
            startActivityWithClear(MainActivity::class.java)
            showToast("로그인 되었습니다.")
        }
        else {
            startActivityWithClear(LoginActivity::class.java)
        }
    }

    private fun getIdx(): Int{
        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        return spf!!.getInt("idx",-1)
    }


}
