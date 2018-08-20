package com.synexoit.weatherapplication.ui.city

import android.arch.lifecycle.ViewModel
import android.support.v7.widget.RecyclerView
import com.synexoit.weatherapplication.data.entity.DayDetails
import com.synexoit.weatherapplication.databinding.ItemDayDetailsBinding
import com.synexoit.weatherapplication.ui.base.adapter.BaseBindingAdapter
import com.synexoit.weatherapplication.util.ViewType

/**
 * Created by dawidjamrozy on 14.05.2018.
 */
class DayDetailsDelegateAdapter(private val list: List<ViewType>) : BaseBindingAdapter<ItemDayDetailsBinding>()  {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, viewModel: ViewModel?) {
        @Suppress("UNCHECKED_CAST")
        holder as BindingViewHolder<ItemDayDetailsBinding>
        val position = holder.adapterPosition
        val item = list[position] as DayDetails
        holder.binding.item = item
    }
}