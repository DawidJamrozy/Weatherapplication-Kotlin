package com.synexoit.weatherapp.data.entity

import android.os.Parcelable
import android.support.annotation.DrawableRes
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.util.ViewType
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