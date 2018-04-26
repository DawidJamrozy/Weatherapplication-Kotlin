package com.synexoit.weatherapp.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.util.ViewType
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "city_place")
data class CityPlace(val name: String,
                     val address: String,
                     val latitude: Double,
                     val longitude: Double,
                     val id: String) : ViewType, Parcelable {

    @PrimaryKey(autoGenerate = true)
    var roomiId: Long = 0

    override fun getViewType(): Int = R.layout.item_place
}