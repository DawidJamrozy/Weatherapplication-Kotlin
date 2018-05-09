package com.synexoit.weatherapp.data.entity.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "currently")
data class Currently(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                     var time: Int?,
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
                     @ForeignKey(entity = City::class, parentColumns = ["id"], childColumns = ["cityId"])
                     var cityId: Long = 0) : Parcelable