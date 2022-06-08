package com.se.hanger.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.se.hanger.view.calendar.CalendarFragment
import com.se.hanger.view.cloth.ClothFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    val mainFragmentList = listOf<Fragment>(ClothFragment(), CalendarFragment())

    override fun getItemCount() =mainFragmentList.size


    override fun createFragment(position: Int): Fragment {
        return mainFragmentList[position]
    }
}