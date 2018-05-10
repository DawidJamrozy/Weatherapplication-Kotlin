package com.synexoit.weatherapp.data.entity.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "hourly_data",
        foreignKeys = [ForeignKey(entity = Hourly::class, parentColumns = ["id"], childColumns = ["hourlyId"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)])
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
                      var cloudCover: Int?,
                      var pressure: Double?,
                      var ozone: Double?,
                      var precipType: String?,
                      var hourlyId: Long = 0,
                      @PrimaryKey(autoGenerate = true)
                      var roomId: Long = 0) : Parcelable {

    constructor() : this(0, "","", 0.0, 0.0,
            0.0, 0.0,0.0,0.0,0.0,
            0.0,0,0.0,0.0,"", 0 ,0)
}
