package com.synexoit.weatherapp.data.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity

/**
 * Created by dawidjamrozy on 14.05.2018.
 */
inline fun <reified T: ViewModel> FragmentActivity.getViewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val viewModel = ViewModelProviders.of(this, factory)[T::class.java]
    viewModel.body()
    return viewModel
}
