package com.example.weatherapp.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val weatherService: WeatherService
) {
    suspend fun fetchWeatherForecast(city: String) = withContext(ioDispatcher) {
        weatherService.fetchWeatherForecast(
            city = city
        )
    }
}