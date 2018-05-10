package com.synexoit.weatherapp.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.synexoit.weatherapp.data.db.dao.*
import com.synexoit.weatherapp.data.entity.darksky.*

@Database(entities = [City::class, Hourly::class, Currently::class, Daily::class, HourlyData::class, DailyData::class], version = 1)
@TypeConverters()
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCityDao(): CityDao
    abstract fun getHourlyDao(): HourlyDao
    abstract fun getHourlyDataDao(): HourlyDataDao
    abstract fun getDailyDao(): DailyDao
    abstract fun getDailyDataDao(): DailyDataDao
    abstract fun getCurrentlyDao(): CurrentlyDao
}

class TypeConverters {

}