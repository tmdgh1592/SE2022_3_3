package com.se.hanger.view.weather

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.se.hanger.R
import com.se.hanger.data.model.Weather
import com.se.hanger.data.retrofit.RetrofitClient
import com.se.hanger.data.retrofit.api.WeatherService
import com.se.hanger.databinding.ActivityWeatherBinding
import com.se.hanger.setStatusBarTransparent
import com.se.hanger.view.cloth.ClothFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityWeatherBinding
    private var retrofit = RetrofitClient.getRetrofit()
    private var job = Job()
    private val _weatherItems = MutableLiveData<List<Weather.Response.Body.Items.Item>>()
    private val weatherItems: LiveData<List<Weather.Response.Body.Items.Item>> = _weatherItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)

        with(binding) {
            val temperature = intent?.getStringExtra("temp")
            temperatureTv.text = "$temperature°C" // 온도를 가져온다.
            timeSelectBtn.setOnClickListener(this@WeatherActivity)
            registerLiveData()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.time_select_btn -> {
                val timePickerDialog =
                    TimePickerDialog(
                        this,
                        { timePickerView, hourOfDay, minute ->
                            // 날씨 데이터를 불러오는 동안 loading View을 보여줌
                            binding.loadingView.visibility = View.GONE
                            loadWeatherData(hourOfDay) // 날씨 데이터를 불러온다.
                        }, 0, 0, true
                    )
                timePickerDialog.show()
            }
        }
    }

    private fun registerLiveData() {
        weatherItems.observe(this) { items ->
            items.forEach { item ->
                if (item.category == "T1H") { // 온도
                    val temperature = item.obsrValue // 날씨 변수 초기화
                    Log.d("TAG", "온도: $temperature")
                    updateTemperature(temperature!!) // 온도 수치 갱신

                    // 날씨 데이터를 모두 불러왔으면 loading View를 숨김
                    binding.loadingView.visibility = View.GONE
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTemperature(temperature: String) {
        binding.temperatureTv.text = "$temperature°C"
    }

    private fun loadWeatherData(hour: Int) {
        CoroutineScope(job + Dispatchers.IO).async {
            val weatherService = retrofit?.create(WeatherService::class.java)
            weatherService?.getWeather(
                RetrofitClient.serviceKey,
                "1",
                "1000",
                "JSON",
                getNowDate(),
                convertHour(hour.toString()),
                ClothFragment.sangmyeongNx,
                ClothFragment.sangmyeongNy
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
                    weather?.response?.body?.items?.item?.let {
                        _weatherItems.value = it
                    } ?: Toast.makeText(this@WeatherActivity, "해당 시간대의 날씨를 불러올 수 없습니다.", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    Log.d("Weather", "onFailure: $t")
                    Toast.makeText(this@WeatherActivity, "날씨 데이터를 불러올 수 없습니다.", Toast.LENGTH_LONG).show()
                }

            })
        }
    }

    private fun convertHour(hour: String): String {
        var convertedHour = hour

        if (hour.length == 1) {
            convertedHour = "0$hour"
        }
        convertedHour += "00"
        Log.d("TAG", "getNowHour: $convertedHour")
        return convertedHour
    }

    @SuppressLint("SimpleDateFormat")
    private fun getNowDate(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        return dateFormat.format(Date())
    }
}