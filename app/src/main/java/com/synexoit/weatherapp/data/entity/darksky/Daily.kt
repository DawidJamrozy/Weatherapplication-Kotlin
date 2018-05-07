package com.synexoit.weatherapp.data.entity.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "daily")
data class Daily(var summary: String?,
                 var icon: String?,
                 //var data: List<DailyData>,
                 @ForeignKey(entity = City::class, parentColumns = ["id"], childColumns = ["cityId"])
                 var cityId: Long = 0,
                 @PrimaryKey(autoGenerate = true)
                 var id: Int = 0) : Parcelable