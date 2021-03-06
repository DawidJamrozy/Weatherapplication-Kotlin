package com.synexoit.weatherapplication.ui.city.adapter

import android.arch.lifecycle.ViewModel
import android.support.v7.widget.RecyclerView
import com.synexoit.weatherapplication.databinding.ItemDayBinding
import com.synexoit.weatherapplication.presentation.data.entity.DayData
import com.synexoit.weatherapplication.presentation.util.ViewType
import com.synexoit.weatherapplication.ui.base.adapter.BaseBindingAdapter

/**
 * Created by Dawid on 10.05.2018.
 */
class DayDelegateAdapter(private val list: List<ViewType>) : BaseBindingAdapter<ItemDayBinding>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, viewModel: ViewModel?) {
        @Suppress("UNCHECKED_CAST")
        holder as BindingViewHolder<ItemDayBinding>
        val position = holder.adapterPosition
        val item = list[position] as DayData
        holder.binding.day = item
    }
}