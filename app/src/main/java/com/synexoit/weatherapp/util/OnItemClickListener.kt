package com.synexoit.weatherapp.util

import android.view.View

/**
 * Created by Dawidj on 02.09.2017.
 */
interface OnItemClickListener {

    fun onItemClick(position: Int, item: ViewType, view: View? = null)

}