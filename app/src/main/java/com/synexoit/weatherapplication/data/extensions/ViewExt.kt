package com.synexoit.weatherapplication.data.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.synexoit.weatherapplication.util.chart.SimpleTextWatcher

/**
 * Created by dawidjamrozy on 14.05.2018.
 */
fun ViewGroup.inflate(layoutId: Int): View = LayoutInflater.from(context).inflate(layoutId, this, false)

fun View.isVisible() = visibility == View.VISIBLE

fun View.visible() { visibility = View.VISIBLE }

fun View.gone() { visibility = View.GONE }

fun View.invisible() { visibility = View.INVISIBLE }

fun View.onFocusChange(onFocusChange: (Boolean) -> Unit) = setOnFocusChangeListener { _, b -> onFocusChange(b) }

fun View.onClick(onClick: () -> Unit) = setOnClickListener { onClick() }

fun View.onLongClick(onLongClick : () -> Boolean) = setOnLongClickListener { onLongClick() }

fun EditText.onTextChange(onTextChange: (String) -> Unit) = addTextChangedListener(object : SimpleTextWatcher() {
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        onTextChange(p0.toString())
    }
})

