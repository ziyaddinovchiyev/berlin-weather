package com.example.weatherapp.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.databinding.ItemDayWeatherBinding

class DaysRecyclerViewAdapter(
    private val hourClickListener: (hourlyWeather: WeatherModel) -> Unit
) : RecyclerView.Adapter<DaysRecyclerViewAdapter.DayViewHolder>() {

    private val days = mutableMapOf<Long, List<WeatherModel>>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(update: Map<Long, List<WeatherModel>>) {
        days.clear()
        days.putAll(update.toSortedMap())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder(
            ItemDayWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            hourClickListener
        )
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        days[days.keys.sorted()[position]]?.let { holder.bind(it) }
    }

    override fun getItemCount() = days.size

    class DayViewHolder(
        private val binding: ItemDayWeatherBinding,
        private val hourClickListener: (hourlyWeather: WeatherModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hours: List<WeatherModel>) {
            binding.dayLabel.text = hours[0].getDate()
            binding.hoursRecyclerView.adapter = HoursRecyclerViewAdapter { hourlyWeather ->
                hourClickListener.invoke(
                    hourlyWeather
                )
            }.apply {
                stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
                updateData(hours)
            }
        }
    }
}