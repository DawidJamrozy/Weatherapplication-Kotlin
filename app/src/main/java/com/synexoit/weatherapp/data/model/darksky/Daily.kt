package com.synexoit.weatherapp.data.model.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "daily")
data class Daily(var summary: String,
                 var icon: String,
                 var data: List<DailyData>,
                 @PrimaryKey(autoGenerate = true)
                 var roomId: Int = 0) : Parcelable