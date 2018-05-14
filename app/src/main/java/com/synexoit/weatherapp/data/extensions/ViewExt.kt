package com.synexoit.weatherapp.data.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by dawidjamrozy on 14.05.2018.
 */
fun ViewGroup.inflate(layoutId: Int): View = LayoutInflater.from(this.context).inflate(layoutId, this, false)

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() { this.visibility = View.VISIBLE }

fun View.invisible() { this.visibility = View.GONE }