package com.synexoit.weatherapp.data.entity.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "daily_data",
        foreignKeys = [ForeignKey(entity = Daily::class, parentColumns = ["id"], childColumns = ["dailyId"], onDelete = CASCADE, onUpdate = CASCADE)])
data class DailyData(var time: Int?,
                     var summary: String?,
                     var placeId: String?,
                     var icon: String?,
                     var sunriseTime: Double?,
                     var sunsetTime: Double?,
                     var moonPhase: Double?,
                     var precipIntensity: Double?,
                     var precipIntensityMax: Double?,
                     var precipIntensityMaxTime: Double?,
                     var precipProbability: Double?,
                     var precipType: String?,
                     var temperatureMin: Double?,
                     var temperatureMinTime: Double?,
                     var temperatureMax: Double?,
                     var temperatureMaxTime: Double?,
                     var apparentTemperatureMin: Double?,
                     var apparentTemperatureMinTime: Double?,
                     var apparentTemperatureMax: Double?,
                     var apparentTemperatureMaxTime: Double?,
                     var dewPoint: Double?,
                     var humidity: Double?,
                     var windSpeed: Double?,
                     var windBearing: Double?,
                     var cloudCover: Double?,
                     var pressure: Double?,
                     var ozone: Double?,
                     var dailyId: Long = 0,
                     @PrimaryKey(autoGenerate = true)
                     var roomId: Long = 0) : Parcelable
