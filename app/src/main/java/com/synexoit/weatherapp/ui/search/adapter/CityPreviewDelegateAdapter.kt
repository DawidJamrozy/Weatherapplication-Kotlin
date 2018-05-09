package com.synexoit.weatherapp.ui.search.adapter

import android.arch.lifecycle.ViewModel
import android.support.v7.widget.RecyclerView
import com.example.dawidjamrozy.chattapplication.ui.adapter.delegates.BaseBindingAdapter
import com.synexoit.weatherapp.data.entity.CityPreview
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.databinding.ItemCityPreviewBinding
import com.synexoit.weatherapp.ui.search.SearchViewModel
import com.synexoit.weatherapp.util.ViewType

class CityPreviewDelegateAdapter : BaseBindingAdapter<ItemCityPreviewBinding>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, list: List<ViewType>, viewModel :ViewModel?) {
        @Suppress("UNCHECKED_CAST")
        holder as BindingViewHolder<ItemCityPreviewBinding>
        val position = holder.adapterPosition
        val item = list[position] as CityPreview
        holder.binding.item = item

        viewModel?.let {
            it as SearchViewModel
            holder.binding.vm = it
        }
    }
}