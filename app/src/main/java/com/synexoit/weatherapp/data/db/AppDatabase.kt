package com.synexoit.weatherapp.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.synexoit.weatherapp.data.db.dao.*
import com.synexoit.weatherapp.data.entity.CityPlace
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.entity.darksky.Currently
import com.synexoit.weatherapp.data.entity.darksky.Daily
import com.synexoit.weatherapp.data.entity.darksky.Hourly

@Database(entities = [CityPlace::class, City::class, Hourly::class, Currently::class, Daily::class], version = 1)
@TypeConverters
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCityPlaceDao(): CityPlaceDao
    abstract fun getCityDao(): CityDao
    abstract fun getHourlyDao(): HourlyDao
    abstract fun getDailyDao(): DailyDao
    abstract fun getCurrentlyDao(): CurrentlyDao
}

class TypeConverters {

}