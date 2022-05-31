package com.se.hanger.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.se.hanger.R
import com.se.hanger.view.adapter.MainViewPagerAdapter
import com.se.hanger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: MainViewPagerAdapter
    private val tabIcons = mutableListOf<Int>(R.drawable.ic_home, R.drawable.ic_weather_calendar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewPagerAdapter = MainViewPagerAdapter(this)

        with(binding) {
            viewPager.adapter = viewPagerAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.icon = ContextCompat.getDrawable(this@MainActivity, tabIcons[position])
            }.attach()
        }

    }


}