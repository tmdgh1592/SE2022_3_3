package com.se.hanger.view.weather

import androidx.lifecycle.ViewModelProvider
import com.se.hanger.R
import com.se.hanger.databinding.FragmentWeatherBinding
import com.se.hanger.view.base.BaseFragment

class WeatherFragment(
    override val layoutResId: Int = R.layout.fragment_weather
) : BaseFragment<FragmentWeatherBinding, WeatherViewModel>() {
    override val viewModel: WeatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

    override fun initActivity() {

    }

}