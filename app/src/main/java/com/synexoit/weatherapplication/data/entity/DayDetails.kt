package com.synexoit.weatherapplication.data.entity

import android.os.Parcelable
import android.support.annotation.DrawableRes
import com.synexoit.weatherapplication.R
import com.synexoit.weatherapplication.util.ViewType
import kotlinx.android.parcel.Parcelize

/**
 * Created by dawidjamrozy on 14.05.2018.
 */
@Parcelize
data class DayDetails(val name: String,
                      val value: Int,
                      val unit: String,
                      @DrawableRes val imageRes: Int) : Parcelable, ViewType {

    override fun getViewType(): Int = R.layout.item_day_details

    override fun getUniqueId(): String = name
}