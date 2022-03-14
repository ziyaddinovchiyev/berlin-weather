package com.example.weatherapp.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.utils.ConnectionLiveData
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WeatherListFragment : Fragment(R.layout.fragment_weather_list) {

    @Inject
    lateinit var connectionLiveData: ConnectionLiveData
    private var isInternetAvailable = false

    private lateinit var binding: FragmentWeatherListBinding
    private val viewModel: WeatherViewModel by viewModels()

    private val adapter = DaysRecyclerViewAdapter { hourlyWeather ->
        findNavController().navigate(
            WeatherListFragmentDirections.actionWeatherListFragmentToWeatherDetailFragment(
                viewModel.city.value,
                hourlyWeather
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupObservers() {
        connectionLiveData.observe(viewLifecycleOwner) {
            isInternetAvailable = it ?: false
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherForecast.collectLatest {
                    adapter.updateData(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.error.collectLatest {
                Toast.makeText(requireContext(), getString(R.string.network_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupUI() {
        binding = FragmentWeatherListBinding.bind(requireView()).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.vm = viewModel
        }.apply {
            daysRecyclerView.adapter = adapter.apply {
                stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
            retry.setOnClickListener {
                fetchWeatherForecast()
            }
            refresh.setOnClickListener {
                fetchWeatherForecast()
            }
        }
    }

    private fun fetchWeatherForecast() {
        if (isInternetAvailable) {
            viewModel.fetchWeatherForecast()
        } else {
            Toast.makeText(requireContext(), getString(R.string.internet_unavailable),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}