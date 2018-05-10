package com.synexoit.weatherapp.data.entity.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.synexoit.weatherapp.data.entity.CityPlace
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "city", indices = [Index(value = ["placeId"], unique = true)])
data class City(@PrimaryKey(autoGenerate = true)
                var id: Long = 0,
                var placeId: String = "",
                var name: String = "",
                var address: String = "",
                var addressDescription: String = "",
                var refreshDate: String = "",
                var sortPosition: Int = 0,
                var latitude: Double = 0.0,
                var longitude: Double = 0.0,
                var timezone: String = "",
                @Ignore var currently: Currently?,
                @Ignore var hourly: Hourly?,
                @Ignore var daily: Daily?) : Parcelable {


    fun toCityPlace(): CityPlace {
        return CityPlace(name, address, latitude, longitude, placeId)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is City) return false

        if (placeId != other.placeId) return false

        return true
    }

    override fun hashCode(): Int {
        return placeId.hashCode()
    }

    constructor() : this(0, "", "", "", "", "", 0, 0.0, 0.0, "", null, null, null)

}
