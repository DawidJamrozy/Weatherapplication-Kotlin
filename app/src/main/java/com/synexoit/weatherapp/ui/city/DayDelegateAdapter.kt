package com.synexoit.weatherapp.ui.city

import android.arch.lifecycle.ViewModel
import android.support.v7.widget.RecyclerView
import com.example.dawidjamrozy.chattapplication.ui.adapter.delegates.BaseBindingAdapter
import com.synexoit.weatherapp.data.entity.darksky.DayData
import com.synexoit.weatherapp.databinding.ItemDayBinding
import com.synexoit.weatherapp.util.ViewType

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