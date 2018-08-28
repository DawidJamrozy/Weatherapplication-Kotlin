package com.synexoit.weatherapplication.ui.search.adapter

import android.arch.lifecycle.ViewModel
import android.support.v7.widget.RecyclerView
import com.synexoit.weatherapplication.presentation.data.entity.CityPreview
import com.synexoit.weatherapplication.databinding.ItemCityPreviewBinding
import com.synexoit.weatherapplication.ui.base.adapter.BaseBindingAdapter
import com.synexoit.weatherapplication.presentation.viewmodel.search.SearchViewModel
import com.synexoit.weatherapplication.util.ItemTouchHelperAdapter
import com.synexoit.weatherapplication.presentation.data.util.ViewType

class CityPreviewDelegateAdapter(private val list: List<ViewType>) : BaseBindingAdapter<ItemCityPreviewBinding>(), ItemTouchHelperAdapter {

    private lateinit var viewModel: SearchViewModel

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, viewModel :ViewModel?) {
        @Suppress("UNCHECKED_CAST")
        holder as BindingViewHolder<ItemCityPreviewBinding>
        val position = holder.adapterPosition
        val item = list[position] as CityPreview
        this.viewModel = viewModel as SearchViewModel
        holder.binding.item = item
        holder.binding.vm = this.viewModel
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        viewModel.itemsMoved(fromPosition, toPosition)
    }
}