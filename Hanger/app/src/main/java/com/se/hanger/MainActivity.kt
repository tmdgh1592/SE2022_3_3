package com.se.hanger

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.se.hanger.databinding.ActivityMainBinding
import com.se.hanger.model.Weather
import com.se.hanger.retrofit.RetrofitClient
import com.se.hanger.retrofit.api.WeatherService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var job = Job()
    private lateinit var binding: ActivityMainBinding
    private var retrofit = RetrofitClient.getRetrofit()


    companion object {
        // 실수 입력 불가 e.g. 37.651234
        const val sangmyeongNx = "38"
        const val sangmyeongNy = "127"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        CoroutineScope(Dispatchers.IO).launch {
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
            )?.enqueue(object: Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    Log.d("Weather", "onResponse: $response")

                    val weather = response.body() as Weather
                    //Log.d("Weather", "loaded Data: $weather")

                    // category 종류
                    // T1H(기온 ℃), RN1(1시간 강수량 mm), UUU(동서바람성분 m/s), VVV(남북바람성분 m/s)
                    // REH(습도 %), PTY(강수형태), VEC(풍향 deg), WSD(풍속 m/s)
                    // PTY : 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4), 빗방울(5), 빗방울/눈날림(6), 눈날림(7)
                    // obsrValue : 수치
                    weather.response.body.items.item.forEach { item->
                        Log.d("TAG", "onResponse: $item")
                    }

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


}