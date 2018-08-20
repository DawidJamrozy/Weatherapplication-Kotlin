package com.synexoit.weatherapplication.cache.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "currentlyCache",
        foreignKeys = [ForeignKey(entity = CityCache::class, parentColumns = ["id"], childColumns = ["cityId"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)])
data class CurrentlyCache(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                          var time: Long = 0,
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
                          var cloudCover: Double = 0.0,
                          var pressure: Double = 0.0,
                          var ozone: Double = 0.0,
                          var cityId: Long = 0) {

    constructor() : this(0, 0, "", "", 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0)
}