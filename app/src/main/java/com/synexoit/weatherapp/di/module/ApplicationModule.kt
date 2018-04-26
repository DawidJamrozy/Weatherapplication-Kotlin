package com.synexoit.weatherapp.di.module

import dagger.Module

@Module(includes = [ApiModule::class, ViewModelModule::class, DatabaseModule::class, RepositoryModule::class])
class ApplicationModule {

}