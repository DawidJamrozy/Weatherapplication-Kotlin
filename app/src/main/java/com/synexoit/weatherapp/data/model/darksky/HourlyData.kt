package com.synexoit.weatherapp.data.model.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "hourly_data")
data class HourlyData(var time: Int?,
                      var summary: String?,
                      var icon: String?,
                      var precipIntensity: Double?,
                      var precipProbability: Double?,
                      var temperature: Double?,
                      var apparentTemperature: Double?,
                      var dewPoint: Double?,
                      var humidity: Double?,
                      var windSpeed: Double?,
                      var windBearing: Double?,
                      var cloudCover: Double?,
                      var pressure: Double?,
                      var ozone: Double?,
                      var precipType: String?,
                      @PrimaryKey(autoGenerate = true)
                      var roomId: Long = 0) : Parcelable
