package com.synexoit.weatherapplication.cache.module

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
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabaseInstance(application: Context): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "database")
                .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(application: Context): SharedPreferences {
        return application.getSharedPreferences("com.synexoit.weatherapplication_preferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesManager(sharedPreferences: SharedPreferences): SharedPreferencesManager {
        return SharedPreferencesManager(sharedPreferences)
    }

}