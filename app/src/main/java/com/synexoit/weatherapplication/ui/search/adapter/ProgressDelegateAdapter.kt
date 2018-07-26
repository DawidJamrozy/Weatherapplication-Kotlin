package com.synexoit.weatherapplication.ui.search.adapter

import android.arch.lifecycle.ViewModel
import android.support.v7.widget.RecyclerView
import com.synexoit.weatherapplication.data.extensions.visible
import com.synexoit.weatherapplication.databinding.ItemProgressBinding
import com.synexoit.weatherapplication.ui.base.adapter.BaseBindingAdapter

/**
 * Created by Dawid on 25.12.2017.
 */
class ProgressDelegateAdapter : BaseBindingAdapter<ItemProgressBinding>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, viewModel: ViewModel?) {
        @Suppress("UNCHECKED_CAST")
        holder as BindingViewHolder<ItemProgressBinding>
        holder.binding.progressBar.visible()
    }
}
