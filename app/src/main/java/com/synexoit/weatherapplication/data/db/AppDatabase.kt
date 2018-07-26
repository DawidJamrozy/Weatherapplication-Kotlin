package com.synexoit.weatherapplication.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import com.synexoit.weatherapplication.data.db.dao.*
import com.synexoit.weatherapplication.data.entity.darksky.*
import java.util.*

@Database(entities = [City::class, Hourly::class, Currently::class, Daily::class, HourlyData::class, DailyData::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCityDao(): CityDao
    abstract fun getHourlyDao(): HourlyDao
    abstract fun getHourlyDataDao(): HourlyDataDao
    abstract fun getDailyDao(): DailyDao
    abstract fun getDailyDataDao(): DailyDataDao
    abstract fun getCurrentlyDao(): CurrentlyDao

}

class RoomConverters {

    @TypeConverter
    fun toData(time: Long) = Date(time)

    @TypeConverter
    fun fromDate(date: Date) = date.time

}