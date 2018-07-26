package com.synexoit.weatherapplication.data.entity.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "currently",
        foreignKeys = [ForeignKey(entity = City::class, parentColumns = ["id"], childColumns = ["cityId"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)])
data class Currently(@PrimaryKey(autoGenerate = true) var id: Long = 0,
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
                     var cityId: Long = 0) : Parcelable