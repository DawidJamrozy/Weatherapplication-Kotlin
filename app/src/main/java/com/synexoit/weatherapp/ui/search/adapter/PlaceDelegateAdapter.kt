package com.synexoit.weatherapp.ui.search.adapter

import android.support.v7.widget.RecyclerView
import com.example.dawidjamrozy.chattapplication.ui.adapter.delegates.BaseBindingAdapter
import com.synexoit.weatherapp.data.entity.CityPlace
import com.synexoit.weatherapp.databinding.ItemPlaceBinding
import com.synexoit.weatherapp.util.OnItemClickListener
import com.synexoit.weatherapp.util.ViewType

class PlaceDelegateAdapter : BaseBindingAdapter<ItemPlaceBinding>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, list: List<ViewType>, listener: OnItemClickListener?) {
        @Suppress("UNCHECKED_CAST")
        holder as BindingViewHolder<ItemPlaceBinding>
        val position = holder.adapterPosition
        val item = list[position] as CityPlace
        holder.binding.item = item

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }
}