package com.synexoit.weatherapp.di.module

import android.arch.persistence.room.Room
import com.synexoit.weatherapp.WeatherApplication
import com.synexoit.weatherapp.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabaseInstance(application: WeatherApplication) : AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "database").build()
    }
}