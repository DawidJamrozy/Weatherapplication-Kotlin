package com.synexoit.weatherapplication.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import com.synexoit.weatherapplication.cache.dao.*
import com.synexoit.weatherapplication.cache.entity.*
import java.util.*

@Database(entities = [CityCache::class, HourlyCache::class, CurrentlyCache::class, DailyCache::class, HourlyDataCache::class, DailyDataCache::class], version = 1)
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