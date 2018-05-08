package com.synexoit.weatherapp.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityPlace(val name: String,
                     val address: String,
                     val latitude: Double,
                     val longitude: Double,
                     val id: String) : Parcelable {

}