package com.synexoit.weatherapplication.di.module

import com.synexoit.weatherapplication.cache.module.DatabaseModule
import com.synexoit.weatherapplication.data.module.RepositoryModule
import com.synexoit.weatherapplication.remote.module.ApiModule
import dagger.Module

@Module(includes = [ApiModule::class, ViewModelModule::class, DatabaseModule::class, RepositoryModule::class])
class ApplicationModule {

}