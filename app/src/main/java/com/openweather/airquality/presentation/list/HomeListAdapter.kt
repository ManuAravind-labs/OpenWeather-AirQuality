package com.openweather.airquality.presentation.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openweather.airquality.domain.model.BaseEntity
import com.openweather.airquality.domain.model.ForcastEntity
import com.openweather.airquality.domain.model.HeaderEntity
import com.openweather.airquality.presentation.list.viewholder.ForeCastViewHolder
import com.openweather.airquality.presentation.list.viewholder.SectionHeaderViewHolder

class HomeListAdapter :
    ListAdapter<BaseEntity, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> SectionHeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ForeCastViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ForeCastViewHolder -> {
                val productItem = getItem(position) as? ForcastEntity
                holder.bind(productItem)
            }

            is SectionHeaderViewHolder -> {
                val headerItem = getItem(position) as? HeaderEntity
                holder.bind(headerItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).viewType == 1) {
            ITEM_VIEW_TYPE_HEADER
        } else {
            ITEM_VIEW_TYPE_ITEM
        }
    }

    companion object {
        const val ITEM_VIEW_TYPE_HEADER = 1
        const val ITEM_VIEW_TYPE_ITEM = 2
        val DIFF_CALLBACK: DiffUtil.ItemCallback<BaseEntity> =
            object : DiffUtil.ItemCallback<BaseEntity>() {
                override fun areItemsTheSame(oldItem: BaseEntity, newItem: BaseEntity) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: BaseEntity, newItem: BaseEntity) =
                    oldItem.viewType == newItem.viewType
            }
    }
}
