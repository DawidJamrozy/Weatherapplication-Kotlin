package com.synexoit.weatherapp.data.entity.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "city")
data class City(@PrimaryKey(autoGenerate = true)
                var id: Long = 0,
                var name: String = "",
                var placeId: String = "",
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is City) return false

        if (placeId != other.placeId) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = placeId.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

    constructor() : this(0, "", "", "", "", "", 0, 0.0, 0.0, "", null, null, null)

}
