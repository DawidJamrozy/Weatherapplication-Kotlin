package com.synexoit.weatherapp.ui.search.adapter

import android.arch.lifecycle.ViewModel
import android.support.v7.widget.RecyclerView
import com.example.dawidjamrozy.chattapplication.ui.adapter.delegates.BaseBindingAdapter
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.databinding.ItemCityBinding
import com.synexoit.weatherapp.ui.search.SearchViewModel
import com.synexoit.weatherapp.util.OnItemClickListener
import com.synexoit.weatherapp.util.ViewType

class CityDelegateAdapter : BaseBindingAdapter<ItemCityBinding>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, list: List<ViewType>, listener: OnItemClickListener?,
                                  viewModel:ViewModel?) {
        @Suppress("UNCHECKED_CAST")
        holder as BindingViewHolder<ItemCityBinding>
        val position = holder.adapterPosition
        val item = list[position] as City
        holder.binding.item = item

        viewModel?.let {
            it as SearchViewModel
            holder.binding.vm = it
        }

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position, item)
        }
    }
}