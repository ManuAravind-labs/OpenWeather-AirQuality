package com.openweather.airquality.presentation.list.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openweather.airquality.databinding.ItemHeaderLayoutBinding
import com.openweather.airquality.domain.model.HeaderEntity

class SectionHeaderViewHolder(private val binding: ItemHeaderLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HeaderEntity?) {
        binding.itemHeaderTitleTextview.text = item?.date.toString()
    }

    companion object {
        fun from(parent: ViewGroup): SectionHeaderViewHolder {
            val binding =
                ItemHeaderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SectionHeaderViewHolder(binding)
        }
    }
}
