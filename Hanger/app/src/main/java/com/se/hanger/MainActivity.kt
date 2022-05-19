package com.se.hanger

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
                    Log.d("Weather", "onResponse: ${response.toString()}")
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    Log.d("Weather", "onFailure: ${t.toString()}")
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
        Log.d("TAG", "getNowHour: " + hour)
        return hour
    }

    private fun getNowDate(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        return dateFormat.format(Date())
    }


}