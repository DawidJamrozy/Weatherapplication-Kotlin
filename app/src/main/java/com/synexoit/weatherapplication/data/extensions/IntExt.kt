package com.synexoit.weatherapplication.data.extensions

import android.content.res.Resources

/**
 * Created by dawidjamrozy on 14.05.2018.
 */
fun Int.dpToPx():Int =  (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.pxToDp():Int =  (this / Resources.getSystem().displayMetrics.density).toInt()
