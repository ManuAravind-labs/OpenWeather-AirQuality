package com.openweather.airquality.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openweather.airquality.databinding.ItemComponentLayoutBinding
import com.openweather.airquality.domain.model.Component

class ComponentViewHolder(private val binding: ItemComponentLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Component) {
        binding.apply {
            itemComponentNameTextView.text = item.text
            itemComponentValueTextview.text = item.value
        }
    }

    companion object {
        fun from(parent: ViewGroup): ComponentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemComponentLayoutBinding.inflate(layoutInflater, parent, false)
            return ComponentViewHolder(binding)
        }
    }
}
