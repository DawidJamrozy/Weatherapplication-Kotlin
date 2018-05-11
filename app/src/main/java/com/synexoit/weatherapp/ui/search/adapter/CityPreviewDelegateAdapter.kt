package com.synexoit.weatherapp.ui.search.adapter

import android.arch.lifecycle.ViewModel
import android.support.v7.widget.RecyclerView
import com.example.dawidjamrozy.chattapplication.ui.adapter.delegates.BaseBindingAdapter
import com.synexoit.weatherapp.data.entity.CityPreview
import com.synexoit.weatherapp.databinding.ItemCityPreviewBinding
import com.synexoit.weatherapp.ui.search.SearchViewModel
import com.synexoit.weatherapp.util.ItemTouchHelperAdapter
import com.synexoit.weatherapp.util.ViewType

class CityPreviewDelegateAdapter(private val list: List<ViewType>) : BaseBindingAdapter<ItemCityPreviewBinding>(), ItemTouchHelperAdapter {

    private lateinit var viewModel: SearchViewModel

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, viewModel :ViewModel?) {
        @Suppress("UNCHECKED_CAST")
        holder as BindingViewHolder<ItemCityPreviewBinding>
        val position = holder.adapterPosition
        val item = list[position] as CityPreview
        holder.binding.item = item
        this.viewModel = viewModel as SearchViewModel
        holder.binding.vm = this.viewModel
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        viewModel.itemsMoved(fromPosition, toPosition)
    }
}