package com.synexoit.weatherapplication.di.module

import com.synexoit.weatherapplication.di.module.DatabaseModule
import com.synexoit.weatherapplication.di.module.RepositoryModule
import com.synexoit.weatherapplication.di.module.ViewModelModule
import dagger.Module

@Module(includes = [ApiModule::class, ViewModelModule::class, DatabaseModule::class, RepositoryModule::class])
class ApplicationModule {

}