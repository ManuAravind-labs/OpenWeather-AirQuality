package com.openweather.airquality.presentation.details

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openweather.airquality.domain.model.Component

class ComponentsAdapter : ListAdapter<Component, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ComponentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val workout = getItem(position)
        if (holder is ComponentViewHolder) {
            holder.bind(workout)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Component> =
            object : DiffUtil.ItemCallback<Component>() {
                override fun areItemsTheSame(oldItem: Component, newItem: Component) =
                    oldItem.text == newItem.text

                override fun areContentsTheSame(oldItem: Component, newItem: Component) =
                    oldItem == newItem
            }
    }
}
