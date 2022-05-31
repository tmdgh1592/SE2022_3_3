package com.se.hanger.data.retrofit.api

import com.se.hanger.data.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("getUltraSrtNcst")
    fun getWeather(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNum: String,
        @Query("numOfRows") numOfRows: String,
        @Query("dataType") dataType: String,
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: String,
        @Query("ny") ny: String,
    ): Call<Weather>
}