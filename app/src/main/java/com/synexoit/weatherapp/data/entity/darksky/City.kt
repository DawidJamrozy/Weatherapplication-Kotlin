package com.synexoit.weatherapp.data.entity.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.util.ViewType
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "city")
data class City(var name: String = "",
                var placeId: String = "",
                var address: String = "",
                var addressDescription: String = "",
                var refreshDate: String = "",
                var sortPosition: Int = 0,
                var latitude: Double = 0.0,
                var longitude: Double = 0.0,
                var timezone: String = "",
                @PrimaryKey(autoGenerate = true)
                var id: Long = 0,
                @Ignore
                var currently: Currently?,
                @Ignore
                var hourly: Hourly?,
                @Ignore
                var daily: Daily?) : ViewType, Parcelable {

    override fun getViewType(): Int = R.layout.item_city

    constructor() : this("", "", "", "", "",0, 0.0, 0.0, "", 0, null, null, null)

}
