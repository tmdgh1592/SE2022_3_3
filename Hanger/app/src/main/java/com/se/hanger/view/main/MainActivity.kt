package com.se.hanger.view.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.se.hanger.R
import com.se.hanger.databinding.ActivityMainBinding
import com.se.hanger.view.adapter.MainViewPagerAdapter
import com.se.hanger.view.cloth.ClothAddDialogFragment
import com.se.hanger.view.cloth.ClothFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: MainViewPagerAdapter
    private val tabIcons = mutableListOf<Int>(R.drawable.ic_home, R.drawable.ic_weather_calendar)

    companion object {
        const val CLOTH_FRAGMENT_TAG = "CLOTH_FRAGMENT_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewPagerAdapter = MainViewPagerAdapter(this)

        with(binding) {
            viewPager.adapter = viewPagerAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.icon = ContextCompat.getDrawable(this@MainActivity, tabIcons[position])
            }.attach()

            addBtn.setOnClickListener(this@MainActivity)
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.add_btn -> {
                val clothDialog = ClothAddDialogFragment().apply {
                    isCancelable = false
                    setClothAddListener(object: ClothAddListener{
                        override fun onAdded() {
                            (viewPagerAdapter.mainFragmentList[0] as ClothFragment).loadClothes()
                        }
                    })
                }
                supportFragmentManager.beginTransaction().add(clothDialog, CLOTH_FRAGMENT_TAG)
                    .commit()
            }
        }
    }

}

interface ClothAddListener {
    fun onAdded()
}