package com.synexoit.weatherapplication.di.module

import com.synexoit.weatherapplication.cache.module.DatabaseModule
import dagger.Module

@Module(includes = [ApiModule::class, ViewModelModule::class, DatabaseModule::class, RepositoryModule::class])
class ApplicationModule {

}