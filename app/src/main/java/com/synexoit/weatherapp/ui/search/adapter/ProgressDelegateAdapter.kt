package com.synexoit.weatherapp.ui.search.adapter

import android.arch.lifecycle.ViewModel
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.dawidjamrozy.chattapplication.ui.adapter.delegates.BaseBindingAdapter
import com.synexoit.weatherapp.databinding.ItemProgressBinding

/**
 * Created by Dawid on 25.12.2017.
 */
class ProgressDelegateAdapter : BaseBindingAdapter<ItemProgressBinding>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, viewModel: ViewModel?) {
        @Suppress("UNCHECKED_CAST")
        holder as BindingViewHolder<ItemProgressBinding>
        holder.binding.progressBar.visibility = View.VISIBLE
    }
}
