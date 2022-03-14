package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.WeatherRemoteDataSource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource
) {
    suspend fun fetchWeatherForecast(city: String) =
        remoteDataSource.fetchWeatherForecast(city = city)
}