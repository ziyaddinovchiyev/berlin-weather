package com.example.weatherapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherDetailBinding

class WeatherDetailFragment : Fragment(R.layout.fragment_weather_detail) {

    private lateinit var binding: FragmentWeatherDetailBinding
    private val args: WeatherDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherDetailBinding.bind(requireView()).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.weather = args.hourlyWeather
            it.city = args.city
        }.apply {
            navigateUp.setOnClickListener {
                findNavController().navigateUp()
            }
            temperatureLabel.text = when {
                args.hourlyWeather.mainWeatherInfo.temperature > 15 -> {
                    getString(R.string.hot_label)
                }
                args.hourlyWeather.mainWeatherInfo.temperature < 10 -> {
                    getString(R.string.cold_label)
                }
                else -> {
                    getString(R.string.warm_label)
                }
            }
        }
    }
}