package com.se.hanger.view.cloth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.navigation.NavigationView
import com.se.hanger.R
import com.se.hanger.data.db.ClothDatabase
import com.se.hanger.data.model.Cloth
import com.se.hanger.data.model.Weather
import com.se.hanger.data.retrofit.RetrofitClient
import com.se.hanger.data.retrofit.api.WeatherService
import com.se.hanger.databinding.FragmentClothBinding
import com.se.hanger.view.adapter.ClothRvAdapter
import com.se.hanger.view.info.MyInfoActivity
import com.se.hanger.view.weather.WeatherActivity
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ClothFragment : Fragment(), View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    private var retrofit = RetrofitClient.getRetrofit()
    private var job = Job()
    private val _weatherItems = MutableLiveData<List<Weather.Response.Body.Items.Item>>()
    private val weatherItems: LiveData<List<Weather.Response.Body.Items.Item>> = _weatherItems
    lateinit var binding: FragmentClothBinding
    private var temperature: String? = null // 온도
    private lateinit var clothDB: ClothDatabase
    private lateinit var clothAdapter: ClothRvAdapter

    companion object {
        // 실수 입력 불가 e.g. 37.651234
        const val sangmyeongNx = "38"
        const val sangmyeongNy = "127"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClothBinding.inflate(inflater)
        clothDB = ClothDatabase.getInstance(requireContext())!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 비동기적으로 날씨 데이터를 불러온다.
        loadWeatherData()
        // 데이터를 가져올 경우 갱신할 UI를 위한 LiveData
        registerLiveData()
        setClickListener() // 클릭 리스너 설정
        setDrawerToggle() // Drawer 열리고 닫힐 때 설정
        setRecyclerView() // 리사이클러뷰 설정
    }

    private fun setRecyclerView() {
        clothAdapter = ClothRvAdapter(
            mutableListOf()
        )
        // 의류 삭제 클릭 리스너
        clothAdapter.setClickListener(object : OnClickDeleteButton {
            override fun delete(item: Cloth) {
                AlertDialog.Builder(requireContext()).setTitle("의류 삭제")
                    .setMessage("선택한 의류를 삭제하시겠습니까?")
                    .setPositiveButton("확인") { _, _ ->
                        CoroutineScope(Dispatchers.IO).launch {
                            clothDB.clothDao().delete(item)
                            loadClothes()
                        }
                    }.setNegativeButton("취소") { _, _ -> }
                    .create().show()
            }
        })

        binding.clothRv.adapter = clothAdapter
        loadClothes() // DB에서 Cloth를 불러와 적용한다.
    }

    fun loadClothes() {
        CoroutineScope(Dispatchers.IO).launch {
            val clothes = clothDB.clothDao().getClothes()
            withContext(Dispatchers.Main) {
                clothAdapter.updateItem(clothes)
            }
        }
    }


    private fun setClickListener() {
        with(binding) {
            weatherBtn.setOnClickListener(this@ClothFragment)
            menuBtn.setOnClickListener(this@ClothFragment)
            navigationView.setNavigationItemSelectedListener(this@ClothFragment)
            settingBtn.setOnClickListener(this@ClothFragment)
        }
    }


    private fun registerLiveData() {
        weatherItems.observe(viewLifecycleOwner) { items ->
            items.forEach { item ->
                if (item.category == "PTY") { // 현재 날씨 상태 : PTY
                    val state = item.obsrValue.toInt()
                    Log.d("TAG", ": $state")
                    updateWeather(state) // 날씨에 따라 UI 갱신

                } else if (item.category == "T1H") { // 온도
                    temperature = item.obsrValue // 날씨 변수 초기화
                    Log.d("TAG", "온도: $temperature")
                    updateTemperature(temperature!!) // 온도 수치 갱신
                }
            }
        }
    }

    private fun updateTemperature(temperature: String) {
        binding.weatherTv.text = binding.weatherTv.text.toString() + " $temperature°C"
    }

    private fun updateWeather(state: Int) {
        // PTY : 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4), 빗방울(5), 빗방울/눈날림(6), 눈날림(7)
        when (state) {
            0 -> {
                binding.weatherIv.setImageResource(R.drawable.ic_weather_sunny)
                binding.weatherTv.text = "맑음"
            }
            1, 2, 4, 5, 6 -> {
                binding.weatherIv.setImageResource(R.drawable.ic_weather_rain)
                binding.weatherTv.text = "비내림"
            }
            3, 7 -> {
                binding.weatherIv.setImageResource(R.drawable.ic_weather_snow)
                binding.weatherTv.text = "눈내림"
            }
        }
    }

    private fun loadWeatherData() {
        CoroutineScope(job + Dispatchers.IO).async {
            val weatherService = retrofit?.create(WeatherService::class.java)
            weatherService?.getWeather(
                RetrofitClient.serviceKey,
                "1",
                "1000",
                "JSON",
                getNowDate(),
                getNowHour(),
                sangmyeongNx,
                sangmyeongNy
            )?.enqueue(object : Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    Log.d("Weather", "onResponse: $response")

                    val weather = response.body() as Weather?
                    //Log.d("Weather", "loaded Data: $weather")

                    // category 종류
                    // T1H(기온 ℃), RN1(1시간 강수량 mm), UUU(동서바람성분 m/s), VVV(남북바람성분 m/s)
                    // REH(습도 %), PTY(강수형태), VEC(풍향 deg), WSD(풍속 m/s)
                    // PTY : 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4), 빗방울(5), 빗방울/눈날림(6), 눈날림(7)
                    // obsrValue : 수치
                    weather?.response?.body?.items?.item?.forEach { item ->
                        Log.d("TAG", "onResponse: $item")
                    }
                    // 가져온 데이터 할당
                    _weatherItems.value = weather?.response?.body?.items?.item


                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    Log.d("Weather", "onFailure: $t")
                }

            })
        }
    }

    private fun getNowHour(): String {
        var hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
        if (hour.length == 1) {
            hour = "0$hour"
        }
        hour += "00"
        Log.d("TAG", "getNowHour: $hour")
        return hour
    }

    @SuppressLint("SimpleDateFormat")
    private fun getNowDate(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        return dateFormat.format(Date())
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.weather_btn -> { // 날씨 버튼 클릭시 Weather Fragment로 전환
                val intent = Intent(requireContext(), WeatherActivity::class.java)
                intent.putExtra("temp", temperature)
                startActivity(intent)
            }
            R.id.menu_btn -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
            R.id.setting_btn -> {
                startActivity(Intent(requireActivity(), MyInfoActivity::class.java))
            }
        }
    }

    private fun setDrawerToggle() {
        val drawerToggle = object : ActionBarDrawerToggle(
            requireActivity(), binding.drawerLayout, 0, 0
        ) {
            // Drawer 열리고 닫힐 때, 계절 선택으로 초기화
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                changeDrawerMenu(R.menu.navigation_season_menu)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                changeDrawerMenu(R.menu.navigation_season_menu)
            }
        }

        binding.drawerLayout.addDrawerListener(drawerToggle)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Season
            R.id.spring -> {
                changeDrawerMenu(R.menu.navigation_clothes_menu)
            }
            R.id.summer -> {
                changeDrawerMenu(R.menu.navigation_clothes_menu)
            }
            R.id.fall -> {
                changeDrawerMenu(R.menu.navigation_clothes_menu)
            }
            R.id.winter -> {
                Toast.makeText(requireContext(), "하이", Toast.LENGTH_SHORT).show()
                changeDrawerMenu(R.menu.navigation_clothes_menu)
            }

            // Clothes
            R.id.top -> {
                closeDrawer()
            }
            R.id.outer -> {
                closeDrawer()
            }
            R.id.pants -> {
                closeDrawer()
            }
            R.id.one_piece -> {
                closeDrawer()
            }
            R.id.skirt -> {
                closeDrawer()
            }
            R.id.shoes -> {
                closeDrawer()
            }
            R.id.under_wear -> {
                closeDrawer()
            }
            R.id.accessory -> {
                closeDrawer()
            }
        }
        return false
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawers()
    }

    private fun changeDrawerMenu(menuId: Int) {
        binding.navigationView.menu.clear()
        binding.navigationView.inflateMenu(menuId)
        binding.navigationView.invalidate()
    }

}

interface OnClickDeleteButton {
    fun delete(item: Cloth)
}