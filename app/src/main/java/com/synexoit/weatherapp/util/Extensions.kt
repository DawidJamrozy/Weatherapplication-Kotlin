package com.synexoit.weatherapp.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Resources
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.util.SparseArrayCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(layoutId: Int): View = LayoutInflater.from(this.context).inflate(layoutId, this, false)

fun <T : ViewModel> Fragment.getViewModel(modelClass: Class<T>, viewModelFactory: ViewModelProvider.Factory? = null): T {
    return viewModelFactory?.let { ViewModelProviders.of(this, it).get(modelClass) } ?: ViewModelProviders.of(this).get(modelClass)}

fun < T: ViewModel> FragmentActivity.getViewModel(modelClass: Class<T>, viewModelFactory: ViewModelProvider.Factory? = null): T {
    return viewModelFactory?.let { ViewModelProviders.of(this, it).get(modelClass) } ?: ViewModelProviders.of(this).get(modelClass)}

fun Int.dpToPx():Int =  (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.pxToDp():Int =  (this / Resources.getSystem().displayMetrics.density).toInt()

fun List<Any>.realSize(): Int = this.size - 1

fun <E> SparseArrayCompat<E>.putNext(key: Int, value: E): SparseArrayCompat<E> {
    this.put(key, value)
    return this
}
