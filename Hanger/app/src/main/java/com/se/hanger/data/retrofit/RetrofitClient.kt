package com.se.hanger.data.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val WEATHER_BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"
    const val serviceKey =
        "dBA13HtoNsD/e5rHRalqhisQmGXh5FfOTGpqyCIfm0ZcGO7qEKGcabtsEEIf5k1GlFFStcue18GiGGWjgaQEdw=="
    private var retrofit: Retrofit? = null

    fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(WEATHER_BASE_URL)
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build()
        }
        return retrofit
    }


    private fun getGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }
}