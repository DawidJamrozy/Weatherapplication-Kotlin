package com.synexoit.weatherapplication.cache.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "hourly",
        foreignKeys = [ForeignKey(entity = CityCache::class, parentColumns = ["id"], childColumns = ["cityId"], onDelete = CASCADE, onUpdate = CASCADE)])
data class HourlyCache(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                       var summary: String?,
                       var icon: String?,
                       @Ignore
                       var data: List<HourlyDataCache>? = null,
                       var cityId: Long = 0) {

    constructor() : this(0, "", "", null, 0)

}