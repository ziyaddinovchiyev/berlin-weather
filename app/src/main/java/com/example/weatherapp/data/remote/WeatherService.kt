package com.example.weatherapp.data.remote

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.model.WeatherResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/forecast")
    suspend fun fetchWeatherForecast(
        @Query("q") city: String,
        @Query("units") units: String? = "metric",
        @Query("appid") appId: String? = BuildConfig.API_KEY,
    ) : Response<WeatherResponseModel>
}