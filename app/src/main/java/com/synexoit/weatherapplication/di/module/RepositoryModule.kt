package com.synexoit.weatherapplication.di.module

import com.synexoit.weatherapplication.data.repository.CityPreviewRepository
import com.synexoit.weatherapplication.data.repository.CityPreviewRepositoryImpl
import com.synexoit.weatherapplication.data.repository.CityRepository
import com.synexoit.weatherapplication.data.repository.CityRepositoryImpl
import com.synexoit.weatherapplication.data.repository.WeatherRepository
import com.synexoit.weatherapplication.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindWeatherRepository(repository: WeatherRepositoryImpl): WeatherRepository

    @Binds
    internal abstract fun bindCityRepository(repository: CityRepositoryImpl): CityRepository

    @Binds
    internal abstract fun bindCityPreviewRepository(repository: CityPreviewRepositoryImpl): CityPreviewRepository
}