package com.synexoit.weatherapp.data.extensions

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.synexoit.weatherapp.data.exceptions.Failure
import com.synexoit.weatherapp.ui.base.ViewLifecycleFragment

/**
 * Created by dawidjamrozy on 14.05.2018.
 */

// Activity
fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
        liveData.observe(this, Observer(body))

fun <L : LiveData<Failure>> LifecycleOwner.failure(liveData: L, body: (Failure?) -> Unit) =
        liveData.observe(this, Observer(body))

// Fragment
fun <T : Any, L : LiveData<T>> ViewLifecycleFragment.observe(liveData: L, body: (T?) -> Unit) =
        liveData.observe(this, Observer(body))

fun <L : LiveData<Failure>> ViewLifecycleFragment.failure(liveData: L, body: (Failure?) -> Unit) =
        liveData.observe(this, Observer(body))