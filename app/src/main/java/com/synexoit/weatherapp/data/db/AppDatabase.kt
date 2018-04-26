package com.synexoit.weatherapp.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.synexoit.weatherapp.data.model.CityPlace

@Database(entities = [CityPlace::class], version = 1)
@TypeConverters
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCityPlaceDao(): CityPlaceDao
}

class TypeConverters {

}