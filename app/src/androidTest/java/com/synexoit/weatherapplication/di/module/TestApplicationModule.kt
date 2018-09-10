package com.synexoit.weatherapplication.di.module

import com.synexoit.weatherapplication.cache.module.DatabaseModule
import com.synexoit.weatherapplication.presentation.module.ViewModelModule
import com.synexoit.weatherapplication.remote.module.ApiModule
import dagger.Module

@Module(includes = [ApiModule::class, ViewModelModule::class, TestDatabaseModule::class, TestRepositoryModule::class])
class TestApplicationModule