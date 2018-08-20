package com.synexoit.weatherapplication.cache.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "hourly_data",
        foreignKeys = [ForeignKey(entity = HourlyCache::class, parentColumns = ["id"], childColumns = ["hourlyId"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)])
data class HourlyDataCache(var time: Int = 0,
                           var summary: String = "",
                           var icon: String = "",
                           var precipIntensity: Double = 0.0,
                           var precipProbability: Double = 0.0,
                           var temperature: Double = 0.0,
                           var apparentTemperature: Double = 0.0,
                           var dewPoint: Double = 0.0,
                           var humidity: Double = 0.0,
                           var windSpeed: Double = 0.0,
                           var windBearing: Double = 0.0,
                           var cloudCover: Int = 0,
                           var pressure: Double = 0.0,
                           var ozone: Double = 0.0,
                           var precipType: String = "",
                           var hourlyId: Long = 0,
                           @PrimaryKey(autoGenerate = true)
                           var roomId: Long = 0) {

    constructor() : this(0, "", "", 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0, 0.0, 0.0, "", 0, 0)
}
