package com.synexoit.weatherapp.data.model.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.util.ViewType
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "city")
data class City(var name: String?,
                var placeId: String?,
                var addressDescription: String?,
                var refreshDate: String?,
                var sortPosition: Int?,
                var latitude: Double?,
                var longitude: Double?,
                var timezone: String?,
                @PrimaryKey(autoGenerate = true)
                var id: Long,
                @Ignore
                var currently: Currently?,
                @Ignore
                var hourly: Hourly?,
                @Ignore
                var daily: Daily?) : ViewType, Parcelable {

    override fun getViewType(): Int = R.layout.item_city

    constructor() : this("", "", "", "", 0, 0.0, 0.0, "", 0, null, null, null)

}
