package com.synexoit.weatherapplication.util

import android.arch.lifecycle.ViewModel
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by Dawidj on 02.09.2017.
 */
interface ViewTypeDelegateInterface {

	fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

	fun onBindViewHolder(holder: RecyclerView.ViewHolder, viewModel: ViewModel? = null)
}