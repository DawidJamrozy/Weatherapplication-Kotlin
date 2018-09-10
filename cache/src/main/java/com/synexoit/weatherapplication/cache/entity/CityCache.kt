package com.synexoit.weatherapplication.cache.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "city", indices = [Index(value = ["placeId"], unique = true)])
data class CityCache(@PrimaryKey(autoGenerate = true)
                     var id: Long = 0,
                     var placeId: String = "",
                     var name: String = "",
                     var address: String = "",
                     var refreshDate: String = "",
                     var sortPosition: Int = 0,
                     var latitude: Double = 0.0,
                     var longitude: Double = 0.0,
                     var timezone: String = "",
                     @Ignore var currentlyCache: CurrentlyCache?,
                     @Ignore var hourlyCache: HourlyCache?,
                     @Ignore var dailyCache: DailyCache?) {

    constructor() : this(0, "", "", "", "",
            0, 0.0, 0.0, "", null,
            null, null)

}
