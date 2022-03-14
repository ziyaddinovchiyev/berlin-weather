package com.example.weatherapp.data.model

import android.os.Parcelable
import com.example.weatherapp.toDate
import com.example.weatherapp.toDateTime
import com.example.weatherapp.toDateTimeStamp
import com.example.weatherapp.toTime
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlin.math.roundToInt

@Parcelize
data class WeatherResponseModel(
    val cod: String,
    @field:Json(name = "list") val weatherList: @RawValue List<WeatherModel>,
    val city: WeatherCityModel
) : Parcelable {

    fun groupByDays() = weatherList.groupBy {
        it.unixTimestamp.toDateTimeStamp()
    }.toSortedMap()
}

@Parcelize
data class WeatherModel(
    @field:Json(name = "dt") val unixTimestamp: Long,
    @field:Json(name = "dt_txt") val readableTimestamp: String,
    @field:Json(name = "main") val mainWeatherInfo: MainWeatherInfoModel,
    @field:Json(name = "weather") val weatherDescription: List<WeatherDescriptionModel>
) : Parcelable {

    fun getDate() = unixTimestamp.toDate()
    fun getTime() = unixTimestamp.toTime()
    fun getDateTime() = unixTimestamp.toDateTime()
}

@Parcelize
data class MainWeatherInfoModel(
    @field:Json(name = "temp") val temperature: Float,
    @field:Json(name = "temp_min") val minTemperature: Float,
    @field:Json(name = "temp_max") val maxTemperature: Float,
    @field:Json(name = "feels_like") val feelsLikeTemperature: Float,
    val humidity: Float
) : Parcelable {

    fun getTempUI() = temperature.roundToInt()
    fun getFeelsLikeTempUI() = temperature.roundToInt()
    fun getMinTempUI() = minTemperature.roundToInt()
    fun getMaxTempUI() = maxTemperature.roundToInt()
    fun getHumidityUI() = maxTemperature.roundToInt().toString()
}

@Parcelize
data class WeatherDescriptionModel(
    val main: String,
    val description: String,
) : Parcelable

@Parcelize
data class WeatherCityModel(
    val name: String? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null
) : Parcelable {

    fun getSunriseUI() = sunrise?.toTime()
    fun getSunsetUI() = sunrise?.toTime()
}