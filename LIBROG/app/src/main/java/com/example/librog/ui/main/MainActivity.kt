package com.example.librog.ui.main

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.librog.R
import com.example.librog.databinding.ActivityMainBinding
import com.example.librog.ui.BaseActivity


class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var navHostFragment: NavHostFragment

    override fun initAfterBinding() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()

        binding.mainBottomNavigation.itemIconTintList = null
        binding.mainBottomNavigation.setupWithNavController(navController)

        setTheme(R.style.Theme_LIBROG)

    }

    fun controlBottomNavVisibility (){
        if (binding.mainBottomNavigation.visibility == View.VISIBLE){
            binding.mainBottomNavigation.visibility = View.GONE
        }
        else {
            binding.mainBottomNavigation.visibility = View.VISIBLE
        }
    }
}