package com.synexoit.weatherapplication.data.entity.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "daily_data",
        foreignKeys = [ForeignKey(entity = Daily::class, parentColumns = ["id"], childColumns = ["dailyId"], onDelete = CASCADE, onUpdate = CASCADE)])
data class DailyData(var time: Long = 0,
                     var summary: String = "",
                     var placeId: String = "",
                     var icon: String = "",
                     var sunriseTime: Double = 0.0,
                     var sunsetTime: Double = 0.0,
                     var moonPhase: Double = 0.0,
                     var precipIntensity: Double = 0.0,
                     var precipIntensityMax: Double = 0.0,
                     var precipIntensityMaxTime: Double = 0.0,
                     var precipProbability: Double = 0.0,
                     var precipType: String = "",
                     var temperatureMin: Double = 0.0,
                     var temperatureMinTime: Double = 0.0,
                     var temperatureMax: Double = 0.0,
                     var temperatureMaxTime: Double = 0.0,
                     var apparentTemperatureMin: Double = 0.0,
                     var apparentTemperatureMinTime: Double = 0.0,
                     var apparentTemperatureMax: Double = 0.0,
                     var apparentTemperatureMaxTime: Double = 0.0,
                     var dewPoint: Double = 0.0,
                     var humidity: Double = 0.0,
                     var windSpeed: Double = 0.0,
                     var windBearing: Double = 0.0,
                     var cloudCover: Double = 0.0,
                     var pressure: Double = 0.0,
                     var ozone: Double = 0.0,
                     var dailyId: Long = 0,
                     @PrimaryKey(autoGenerate = true)
                     var roomId: Long = 0) : Parcelable
