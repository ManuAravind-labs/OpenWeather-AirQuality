package com.openweather.airquality.presentation.list.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.openweather.airquality.databinding.ItemForcastLayoutBinding
import com.openweather.airquality.domain.model.ForcastEntity
import com.openweather.airquality.presentation.list.HomeFragmentDirections
import com.openweather.airquality.util.CommonUtils
import com.openweather.airquality.util.convertTime

class ForeCastViewHolder(private val binding: ItemForcastLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ForcastEntity?) {
        binding.apply {
            itemForecastDateTextView.text = item?.dt?.toLong()?.convertTime()
            itemForecastStatusTextview.text = CommonUtils.getAirQualityText(item?.aqi)
            itemForecastCardView.setCardBackgroundColor(CommonUtils.getAirQualityColor(item?.aqi))
            itemForecastCardView.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item)
                itemForecastCardView.findNavController().navigate(action)
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): ForeCastViewHolder {
            val binding =
                ItemForcastLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ForeCastViewHolder(binding)
        }
    }
}
