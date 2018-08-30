package com.synexoit.weatherapplication.data.extensions

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.synexoit.weatherapplication.data.exceptions.Failure
import com.synexoit.weatherapplication.ui.base.ViewLifecycleFragment

/**
 * Created by dawidjamrozy on 14.05.2018.
 */

// Activity
fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
        liveData.observe(this, Observer(body))

fun <L : LiveData<Throwable>> LifecycleOwner.failure(liveData: L, body: (Throwable?) -> Unit) =
        liveData.observe(this, Observer(body))

// Fragment
fun <T : Any, L : LiveData<T>> ViewLifecycleFragment.observe(liveData: L, body: (T?) -> Unit) =
        liveData.observe(this, Observer(body))

fun <L : LiveData<Throwable>> ViewLifecycleFragment.failure(liveData: L, body: (Throwable?) -> Unit) =
        liveData.observe(this, Observer(body))