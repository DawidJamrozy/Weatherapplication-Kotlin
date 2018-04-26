package com.synexoit.weatherapp.data.model.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "hourly")
data class Hourly(var summary: String? = null,
                  var icon: String? = null,
                  var data: List<HourlyData>,
                  @PrimaryKey(autoGenerate = true)
                  var roomId: Long = 0) : Parcelable