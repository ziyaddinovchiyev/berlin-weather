package com.example.weatherapp.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.databinding.ItemHourWeatherBinding
import kotlin.math.roundToInt

class HoursRecyclerViewAdapter(
    private val hourClickListener: (hourlyWeather: WeatherModel) -> Unit
) : RecyclerView.Adapter<HoursRecyclerViewAdapter.HourViewHolder>() {

    private val hours = mutableListOf<WeatherModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(update: List<WeatherModel>) {
        hours.clear()
        hours.addAll(update)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        return HourViewHolder(
            ItemHourWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        holder.bind(hours[position])
    }

    override fun getItemCount() = hours.size

    inner class HourViewHolder(private val binding: ItemHourWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hourlyWeather: WeatherModel) {
            with(binding) {
                time.text = hourlyWeather.getTime()
                temp.text = root.context.getString(
                    R.string.temperature,
                    hourlyWeather.mainWeatherInfo.temperature.roundToInt().toString()
                )
                hourContainer.setOnClickListener {
                    hourClickListener.invoke(hourlyWeather)
                }
            }
        }
    }
}