package com.synexoit.weatherapplication.presentation.data.entity

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.synexoit.weatherapplication.presentation.util.ViewType

/**
 * Created by dawidjamrozy on 14.05.2018.
 */
data class DayDetails(@StringRes val name: Int,
                      val value: Int,
                      @StringRes val unit: Int,
                      @DrawableRes val imageRes: Int) : ViewType {

    companion object {
        const val VIEW_TYPE = 1
    }

    override val viewType: Int
        get() = VIEW_TYPE

    override val uniqueId: String
        get() = name.toString()

}