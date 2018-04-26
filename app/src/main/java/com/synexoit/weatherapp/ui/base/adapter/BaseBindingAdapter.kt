package com.example.dawidjamrozy.chattapplication.ui.adapter.delegates

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.synexoit.weatherapp.util.ViewTypeDelegateInterface

/**
 * Created by Dawid on 03.02.2018.
 */
abstract class BaseBindingAdapter<T : ViewDataBinding> : ViewTypeDelegateInterface {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false) as T
		return BindingViewHolder(binding)
	}

	class BindingViewHolder<out T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)
}