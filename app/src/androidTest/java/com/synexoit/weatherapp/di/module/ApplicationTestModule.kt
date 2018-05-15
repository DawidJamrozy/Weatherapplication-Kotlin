package com.synexoit.weatherapp.di

import com.synexoit.weatherapp.di.module.ApiModule
import com.synexoit.weatherapp.di.module.DatabaseModule
import com.synexoit.weatherapp.di.module.ViewModelModule
import com.synexoit.weatherapp.di.repository.RepositoryTestModule
import dagger.Module

@Module(includes = [ApiModule::class, ViewModelModule::class, DatabaseModule::class, RepositoryTestModule::class])
class ApplicationTestModule {
}