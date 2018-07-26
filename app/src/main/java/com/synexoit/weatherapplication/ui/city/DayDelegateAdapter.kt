package com.synexoit.weatherapplication.ui.city

import android.arch.lifecycle.ViewModel
import android.support.v7.widget.RecyclerView
import com.synexoit.weatherapplication.data.entity.darksky.DayData
import com.synexoit.weatherapplication.databinding.ItemDayBinding
import com.synexoit.weatherapplication.ui.base.adapter.BaseBindingAdapter
import com.synexoit.weatherapplication.util.ViewType

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