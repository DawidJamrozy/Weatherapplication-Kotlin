package com.synexoit.weatherapp.data.model

import android.os.Parcelable
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.util.ViewType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityPlace(val name: String,
                     val address: String,
                     val latitude: Double,
                     val longitude: Double,
                     val id: String) : ViewType, Parcelable {

    override fun getViewType(): Int = R.layout.item_place
}