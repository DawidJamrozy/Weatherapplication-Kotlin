package com.synexoit.weatherapplication.di.module

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.content.SharedPreferences
import com.synexoit.weatherapplication.cache.db.AppDatabase
import com.synexoit.weatherapplication.cache.manager.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestDatabaseModule {

    companion object {
        private const val SHARED_PREFERENCES = "com.synexoit.weatherapplication_preferences"
        private const val DATABASE_NAME = "weatherapplication_database_test"
    }

    @Singleton
    @Provides
    fun provideDatabaseInstance(application: Context): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(application: Context): SharedPreferences {
        return application.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesManager(sharedPreferences: SharedPreferences): SharedPreferencesManager {
        return SharedPreferencesManager(sharedPreferences)
    }

}