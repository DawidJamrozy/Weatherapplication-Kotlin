package com.synexoit.weatherapplication.data.entity.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "daily",
        foreignKeys = [ForeignKey(entity = City::class, parentColumns = ["id"], childColumns = ["cityId"], onDelete = CASCADE, onUpdate = CASCADE)])
data class Daily(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                 var summary: String?,
                 var icon: String?,
                 @Ignore
                 var data: List<DailyData>? = null,
                 var cityId: Long = 0) : Parcelable {

    constructor() : this(0, "", "", null, 0)

}