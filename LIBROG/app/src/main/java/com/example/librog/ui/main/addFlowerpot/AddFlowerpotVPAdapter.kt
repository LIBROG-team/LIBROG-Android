package com.example.librog.ui.main.addFlowerpot

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AddFlowerpotVPAdapter(activity: AddFlowerpotActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> UnlockedFlowerpotFragment()
            else -> LockedFlowerpotFragment()
        }
    }


}