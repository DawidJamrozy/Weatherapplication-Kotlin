package com.synexoit.weatherapp.data.entity.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "hourly")
data class Hourly(var summary: String?,
                  var icon: String?,
                  //var data: List<HourlyData>,
                  @ForeignKey(entity = City::class, parentColumns = ["id"], childColumns = ["cityId"])
                  var cityId: Long = 0,
                  @PrimaryKey(autoGenerate = true)
                  var id: Long = 0) : Parcelable