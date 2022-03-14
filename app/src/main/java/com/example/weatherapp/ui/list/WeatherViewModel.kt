package com.example.weatherapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.WeatherCityModel
import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    private val repository: WeatherRepository
) : ViewModel() {

    /** weather forecast grouped by days */
    private val _weatherForecast = MutableStateFlow(mapOf<Long, List<WeatherModel>>())
    val weatherForecast = _weatherForecast.asStateFlow()

    /** city information */
    private val _city = MutableStateFlow(WeatherCityModel())
    val city = _city.asStateFlow()

    /** loading state */
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    /** single time error observable */
    private val _error = MutableSharedFlow<Any>()
    val error = _error.asSharedFlow()

    init {
        fetchWeatherForecast()
    }

    /**
     * does network request to fetch
     */
    fun fetchWeatherForecast() {
        viewModelScope.launch(ioDispatcher) {
            _loading.emit(true)
            try {
                repository.fetchWeatherForecast(city = "Berlin").apply {
                    if (isSuccessful) {
                        body()?.let { body ->
                            _weatherForecast.emit(body.groupByDays())
                            _city.emit(body.city)
                        } ?: kotlin.run {
                            _error.emit("failed to get response body")
                        }
                    } else {
                        _error.emit("network call failed with ${code()}")
                    }
                }
                _loading.emit(false)
            } catch (e: Exception) {
                _error.emit("unexpected exception: ${e.localizedMessage}")
                _loading.emit(false)
            }
        }
    }
}