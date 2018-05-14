package com.synexoit.weatherapp.data.extensions

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.synexoit.weatherapp.data.exceptions.Failure

/**
 * Created by dawidjamrozy on 14.05.2018.
 */
fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
        liveData.observe(this, Observer(body))

fun <L : LiveData<Failure>> LifecycleOwner.failure(liveData: L, body: (Failure?) -> Unit) =
        liveData.observe(this, Observer(body))